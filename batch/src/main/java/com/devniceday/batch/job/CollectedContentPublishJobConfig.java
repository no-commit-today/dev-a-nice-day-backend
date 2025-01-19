package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectionStatus;
import com.devniceday.batch.domain.image.ContentImageStore;
import com.devniceday.batch.job.reader.QuerydslPagingItemReader;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.module.idgenerator.IdGenerator;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchTechContentEntity;
import com.devniceday.support.aws.NotSupportedImageException;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Clock;

import static com.devniceday.storage.db.core.QBatchCollectedContentEntity.batchCollectedContentEntity;

@Configuration
public class CollectedContentPublishJobConfig {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentPublishJobConfig.class);

    private static final String JOB_NAME = "collectedContentPublishJob";
    private static final String STEP_NAME = "collectedContentPublishStep";
    private static final int CHUNK_SIZE = 10;

    private final Clock clock;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final AlertManager alertManager;
    private final ContentImageStore contentImageStore;
    private final IdGenerator idGenerator;

    public CollectedContentPublishJobConfig(
            Clock clock,
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            AlertManager alertManager,
            ContentImageStore contentImageStore,
            IdGenerator idGenerator
    ) {
        this.clock = clock;
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.alertManager = alertManager;
        this.contentImageStore = contentImageStore;
        this.idGenerator = idGenerator;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new SystemClockRunIdIncrementer(clock))
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<BatchCollectedContentEntity, BatchTechContentEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())

                .faultTolerant()
                .skip(NotSupportedImageException.class)
                .listener(new SkipListener<>() {
                    @Override
                    public void onSkipInProcess(BatchCollectedContentEntity item, Throwable t) {
                        log.error("컨텐츠 발행 중 오류 발생 collectedContentId={}", item.getId(), t);
                    }
                })
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<BatchCollectedContentEntity> reader() {
        QuerydslPagingItemReader<BatchCollectedContentEntity> reader = new QuerydslPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(true);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(batchCollectedContentEntity)
                .where(
                        batchCollectedContentEntity.status.eq(CollectionStatus.SUMMARIZED),
                        batchCollectedContentEntity.deleted.isFalse()
                ).orderBy(batchCollectedContentEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public CollectedContentPublishJobItemProcessor processor() {
        return new CollectedContentPublishJobItemProcessor(
                contentImageStore,
                idGenerator,
                alertManager
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<BatchTechContentEntity> writer() {
        return new JpaItemWriterBuilder<BatchTechContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
    }
}
