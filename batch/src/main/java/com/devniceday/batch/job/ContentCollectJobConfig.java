package com.devniceday.batch.job;

import com.devniceday.batch.domain.ContentSubscriber;
import com.devniceday.batch.domain.SubscriptionExceptionProcessor;
import com.devniceday.batch.job.listener.ExceptionAlertJobExecutionListener;
import com.devniceday.batch.job.param.TechContentProviderIdJobParameter;
import com.devniceday.batch.job.reader.QuerydslPagingItemReader;
import com.devniceday.batch.job.writer.JpaItemListWriter;
import com.devniceday.module.idgenerator.IdGenerator;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
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
import java.util.List;
import java.util.Optional;

import static com.devniceday.storage.db.core.QBatchSubscriptionEntity.batchSubscriptionEntity;

@Configuration
public class ContentCollectJobConfig {

    private static final String JOB_NAME = "contentCollectJob";
    private static final String STEP_NAME = "contentCollectStep";
    private static final int CHUNK_SIZE = 10;

    private final Clock clock;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final IdGenerator idGenerator;
    private final SubscriptionMapper subscriptionMapper;
    private final ContentSubscriber contentSubscriber;
    private final SubscriptionExceptionProcessor subscriptionExceptionProcessor;
    private final ExceptionAlertJobExecutionListener exceptionAlertJobExecutionListener;

    public ContentCollectJobConfig(
            Clock clock,
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            IdGenerator idGenerator,
            SubscriptionMapper subscriptionMapper,
            ContentSubscriber contentSubscriber,
            SubscriptionExceptionProcessor subscriptionExceptionProcessor,
            ExceptionAlertJobExecutionListener exceptionAlertJobExecutionListener
    ) {
        this.clock = clock;
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.idGenerator = idGenerator;
        this.subscriptionMapper = subscriptionMapper;
        this.contentSubscriber = contentSubscriber;
        this.subscriptionExceptionProcessor = subscriptionExceptionProcessor;
        this.exceptionAlertJobExecutionListener = exceptionAlertJobExecutionListener;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(new DefaultJobParametersValidator(
                        new String[]{},
                        new String[]{"provider.id"}
                ))
                .incrementer(new SystemClockRunIdIncrementer(clock))
                .start(step())

                .listener(exceptionAlertJobExecutionListener)
                .build();
    }

    @Bean(JOB_NAME + "TechContentProviderIdJobParameter")
    @JobScope
    public TechContentProviderIdJobParameter providerIdJobParameter() {
        return new TechContentProviderIdJobParameter();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<BatchSubscriptionEntity, List<BatchCollectedContentEntity>>chunk(CHUNK_SIZE, txManager)
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
    public QuerydslPagingItemReader<BatchSubscriptionEntity> reader() {
        QuerydslPagingItemReader<BatchSubscriptionEntity> reader = new QuerydslPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(batchSubscriptionEntity)
                .where(
                        providerIdEq(),
                        batchSubscriptionEntity.deleted.isFalse(),
                        batchSubscriptionEntity.disabled.isFalse()
                )
                .orderBy(batchSubscriptionEntity.id.asc())
        );
        return reader;
    }

    @Nullable
    private BooleanExpression providerIdEq() {
        return Optional.ofNullable(providerIdJobParameter().getProviderId())
                .map(batchSubscriptionEntity.providerId::eq)
                .orElse(null);
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<BatchSubscriptionEntity, List<BatchCollectedContentEntity>> processor() {
        return new ContentCollectJobItemProcessor(
                idGenerator,
                subscriptionMapper,
                contentSubscriber
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemListWriter<BatchCollectedContentEntity> writer() {
        JpaItemWriter<BatchCollectedContentEntity> jpaItemWriter = new JpaItemWriterBuilder<BatchCollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
        return new JpaItemListWriter<>(jpaItemWriter);
    }

    @Bean(STEP_NAME + "SkipListener")
    @StepScope
    public SkipListener<BatchSubscriptionEntity, List<BatchCollectedContentEntity>> listener() {
        return new ContentCollectJobSkipListener(subscriptionMapper, subscriptionExceptionProcessor);
    }
}
