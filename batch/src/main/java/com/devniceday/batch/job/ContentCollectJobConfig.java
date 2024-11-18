package com.devniceday.batch.job;

import com.devniceday.batch.domain.ContentSubscriber;
import com.devniceday.batch.param.TechContentProviderIdJobParameter;
import com.devniceday.batch.reader.QuerydslPagingItemReader;
import com.devniceday.batch.writer.JpaItemListWriter;
import com.devniceday.module.idgenerator.IdGenerator;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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

    public ContentCollectJobConfig(
            Clock clock,
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            IdGenerator idGenerator,
            SubscriptionMapper subscriptionMapper,
            ContentSubscriber contentSubscriber
    ) {
        this.clock = clock;
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.idGenerator = idGenerator;
        this.subscriptionMapper = subscriptionMapper;
        this.contentSubscriber = contentSubscriber;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(jobParametersValidator())
                .incrementer(systemClockRunIdIncrementer())
                .start(step())
                .build();
    }

    public SystemClockRunIdIncrementer systemClockRunIdIncrementer() {
        return new SystemClockRunIdIncrementer(clock);
    }


    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{},
                new String[]{"provider.id"}
        );
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
//                .processor(processor())
                .writer(writer())

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
                        batchSubscriptionEntity.deleted.isFalse()
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
}
