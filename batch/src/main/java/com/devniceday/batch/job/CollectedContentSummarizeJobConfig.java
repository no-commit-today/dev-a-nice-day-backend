package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectionStatus;
import com.devniceday.batch.domain.ContentSummarizer;
import com.devniceday.batch.job.reader.QuerydslZeroPagingItemReader;
import com.devniceday.module.alert.AlertManager;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
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
public class CollectedContentSummarizeJobConfig {

    private static final String JOB_NAME = "collectedContentSummarizeJob";
    private static final String STEP_NAME = "collectedContentSummarizeStep";
    private static final int CHUNK_SIZE = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final Clock clock;
    private final AlertManager alertManager;
    private final ContentSummarizer contentSummarizer;

    public CollectedContentSummarizeJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            Clock clock,
            AlertManager alertManager,
            ContentSummarizer contentSummarizer
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.clock = clock;
        this.alertManager = alertManager;
        this.contentSummarizer = contentSummarizer;
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
                .<BatchCollectedContentEntity, BatchCollectedContentEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslZeroPagingItemReader<BatchCollectedContentEntity> reader() {
        QuerydslZeroPagingItemReader<BatchCollectedContentEntity> reader = new QuerydslZeroPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(batchCollectedContentEntity)
                .where(
                        batchCollectedContentEntity.status.eq(CollectionStatus.INIT),
                        batchCollectedContentEntity.deleted.isFalse()
                ).orderBy(batchCollectedContentEntity.id.asc())
        );
        return reader;
    }


    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public CollectedContentSummarizeJobItemProcessor processor() {
        return new CollectedContentSummarizeJobItemProcessor(
                contentSummarizer,
                alertManager
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<BatchCollectedContentEntity> writer() {
        return new JpaItemWriterBuilder<BatchCollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }

}
