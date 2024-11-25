package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectedContentExceptionProcessor;
import com.devniceday.batch.domain.CollectionStatus;
import com.devniceday.batch.domain.ContentInitializer;
import com.devniceday.batch.domain.SubscriptionExceptionProcessor;
import com.devniceday.batch.job.listener.ExceptionAlertJobExecutionListener;
import com.devniceday.batch.job.reader.QuerydslPagingItemReader;
import com.devniceday.batch.job.reader.QuerydslZeroPagingItemReader;
import com.devniceday.storage.db.core.BatchCollectedContentAndSubscriptionProjection;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.QBatchCollectedContentAndSubscriptionProjection;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Clock;

import static com.devniceday.storage.db.core.QBatchCollectedContentEntity.batchCollectedContentEntity;
import static com.devniceday.storage.db.core.QBatchSubscriptionEntity.batchSubscriptionEntity;

@Configuration
public class CollectedContentInitializeJobConfig {

    private static final String JOB_NAME = "collectedContentInitializeJob";
    private static final String STEP_NAME = "collectedContentInitializeStep";
    private static final int CHUNK_SIZE = 10;

    private final Clock clock;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final ExceptionAlertJobExecutionListener exceptionAlertJobExecutionListener;
    private final SubscriptionMapper subscriptionMapper;
    private final ContentInitializer contentInitializer;
    private final SubscriptionExceptionProcessor subscriptionExceptionProcessor;
    private final CollectedContentExceptionProcessor collectedContentExceptionProcessor;

    public CollectedContentInitializeJobConfig(
            Clock clock,
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            ExceptionAlertJobExecutionListener exceptionAlertJobExecutionListener,
            SubscriptionMapper subscriptionMapper,
            ContentInitializer contentInitializer,
            SubscriptionExceptionProcessor subscriptionExceptionProcessor,
            CollectedContentExceptionProcessor collectedContentExceptionProcessor
    ) {
        this.clock = clock;
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.exceptionAlertJobExecutionListener = exceptionAlertJobExecutionListener;
        this.subscriptionMapper = subscriptionMapper;
        this.contentInitializer = contentInitializer;
        this.subscriptionExceptionProcessor = subscriptionExceptionProcessor;
        this.collectedContentExceptionProcessor = collectedContentExceptionProcessor;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new SystemClockRunIdIncrementer(clock))
                .start(step())

                .listener(exceptionAlertJobExecutionListener)
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<BatchCollectedContentAndSubscriptionProjection, BatchCollectedContentEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())

                .faultTolerant()
                .skip(Exception.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(listener())

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<BatchCollectedContentAndSubscriptionProjection> reader() {
        QuerydslZeroPagingItemReader<BatchCollectedContentAndSubscriptionProjection> reader = new QuerydslZeroPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .select(new QBatchCollectedContentAndSubscriptionProjection(
                        batchCollectedContentEntity,
                        batchSubscriptionEntity
                ))
                .from(batchCollectedContentEntity)
                .join(batchSubscriptionEntity)
                .on(batchCollectedContentEntity.subscriptionId.eq(batchSubscriptionEntity.id))
                .where(
                        batchCollectedContentEntity.status.eq(CollectionStatus.COLLECTED),
                        batchCollectedContentEntity.deleted.isFalse(),
                        batchSubscriptionEntity.deleted.isFalse(),
                        batchSubscriptionEntity.disabled.isFalse()
                )
                .orderBy(batchCollectedContentEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<BatchCollectedContentAndSubscriptionProjection, BatchCollectedContentEntity> processor() {
        return new CollectedContentInitializerJobItemProcessor(subscriptionMapper, contentInitializer);
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<BatchCollectedContentEntity> writer() {
        return new JpaItemWriterBuilder<BatchCollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }

    @Bean(STEP_NAME + "SkipListener")
    @StepScope
    public SkipListener<BatchCollectedContentAndSubscriptionProjection, BatchCollectedContentEntity> listener() {
        return new CollectedContentInitializeJobSkipListener(
                subscriptionExceptionProcessor,
                collectedContentExceptionProcessor
        );
    }
}
