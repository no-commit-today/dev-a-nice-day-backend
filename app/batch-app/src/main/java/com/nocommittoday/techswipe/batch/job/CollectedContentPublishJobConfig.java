package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.domain.content.BatchTechContentIdGenerator;
import com.nocommittoday.techswipe.batch.listener.CollectedContentPublishJobSkipListener;
import com.nocommittoday.techswipe.batch.processor.CollectedContentPublishProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.image.exception.ImageApplicationException;
import com.nocommittoday.techswipe.infrastructure.aws.image.ImageStore;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchImageEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

@Configuration
public class CollectedContentPublishJobConfig {

    private static final String JOB_NAME = "collectedContentPublishJob";
    private static final String STEP_NAME = "collectedContentPublishStep";
    private static final int CHUNK_SIZE = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final ImageStore imageStore;
    private final BatchTechContentIdGenerator techContentIdGenerator;
    private final BatchImageEntityMapper imageEntityMapper;

    public CollectedContentPublishJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            ImageStore imageStore,
            BatchTechContentIdGenerator techContentIdGenerator,
            BatchImageEntityMapper imageEntityMapper
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.imageStore = imageStore;
        this.techContentIdGenerator = techContentIdGenerator;
        this.imageEntityMapper = imageEntityMapper;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<CollectedContentEntity, CollectedContentEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())

                .faultTolerant()
                .skip(ImageApplicationException.class)
                .listener(listener())

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<CollectedContentEntity> reader() {
        QuerydslPagingItemReader<CollectedContentEntity> reader = new QuerydslPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(collectedContentEntity)
                .where(
                        collectedContentEntity.status.eq(CollectionStatus.SUMMARIZED),
                        collectedContentEntity.deleted.isFalse()
                ).orderBy(collectedContentEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public CollectedContentPublishProcessor processor() {
        return new CollectedContentPublishProcessor(
                imageStore,
                techContentIdGenerator,
                imageEntityMapper
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<CollectedContentEntity> writer() {
        return new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }

    @Bean(STEP_NAME + "CollectedContentPublishJobSkipListener")
    @StepScope
    public CollectedContentPublishJobSkipListener listener() {
        return new CollectedContentPublishJobSkipListener();
    }
}
