package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.processor.CollectedContentPublishProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.writer.JpaItemTupleWriter;
import com.nocommittoday.techswipe.batch.writer.JpaItemTupleWriterBuilder;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentEntity;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.nocommittoday.techswipe.collection.storage.mysql.QCollectedContentEntity.collectedContentEntity;

@Configuration
@RequiredArgsConstructor
public class CollectedContentPublishJobConfig {

    private static final String JOB_NAME = "collectedContentPublishJob";
    private static final String STEP_NAME = "collectedContentPublishStep";
    private static final int CHUNK_SIZE = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final ImageStoreService imageStoreService;

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        final StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<CollectedContentEntity, Pair<CollectedContentEntity, TechContentEntity>>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<CollectedContentEntity> reader() {
        final QuerydslPagingItemReader<CollectedContentEntity> reader = new QuerydslPagingItemReader<>();
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
        return new CollectedContentPublishProcessor(imageStoreService);
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemTupleWriter<Pair<CollectedContentEntity, TechContentEntity>> writer() {
        return new JpaItemTupleWriterBuilder<Pair<CollectedContentEntity, TechContentEntity>>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }
}
