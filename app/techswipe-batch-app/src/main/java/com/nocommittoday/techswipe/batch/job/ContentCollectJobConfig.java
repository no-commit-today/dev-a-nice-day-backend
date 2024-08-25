package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.domain.collection.BatchCollectedContentIdGenerator;
import com.nocommittoday.techswipe.batch.domain.collection.BatchSubscribedContentCollectService;
import com.nocommittoday.techswipe.batch.param.TechContentProviderIdJobParameter;
import com.nocommittoday.techswipe.batch.processor.ContentCollectJobItemProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.writer.JpaItemListWriter;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchCollectedContentEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.Optional;

import static com.nocommittoday.techswipe.storage.mysql.subscription.QSubscriptionEntity.subscriptionEntity;

@Configuration
public class ContentCollectJobConfig {

    private static final String JOB_NAME = "contentCollectJob";
    private static final String STEP_NAME = "contentCollectStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final BatchSubscribedContentCollectService subscribedContentCollectService;
    private final BatchCollectedContentIdGenerator collectedContentIdGenerator;
    private final BatchCollectedContentEntityMapper collectedContentEntityMapper;

    public ContentCollectJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            BatchSubscribedContentCollectService subscribedContentCollectService,
            BatchCollectedContentIdGenerator collectedContentIdGenerator,
            BatchCollectedContentEntityMapper collectedContentEntityMapper
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.subscribedContentCollectService = subscribedContentCollectService;
        this.collectedContentIdGenerator = collectedContentIdGenerator;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(jobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{},
                new String[]{"provider.id"}
        );
    }

    @Bean(JOB_NAME + TechContentProviderIdJobParameter.NAME)
    @JobScope
    public TechContentProviderIdJobParameter providerIdListJobParameter() {
        return new TechContentProviderIdJobParameter();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<SubscriptionEntity, List<CollectedContentEntity>>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<SubscriptionEntity> reader() {
        QuerydslPagingItemReader<SubscriptionEntity> reader = new QuerydslPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(subscriptionEntity)
                .where(
                        providerIdEq(),
                        subscriptionEntity.deleted.isFalse()
                )
                .orderBy(subscriptionEntity.id.asc())
        );
        return reader;
    }

    private BooleanExpression providerIdEq() {
        return Optional.ofNullable(providerIdListJobParameter().getProviderId())
                .map(providerId -> subscriptionEntity.provider.id.eq(providerId.value()))
                .orElse(null);
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> processor() {
        return new ContentCollectJobItemProcessor(
                subscribedContentCollectService,
                collectedContentIdGenerator,
                collectedContentEntityMapper
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemListWriter<CollectedContentEntity> writer() {
        JpaItemWriter<CollectedContentEntity> jpaItemWriter = new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
        return new JpaItemListWriter<>(jpaItemWriter);
    }

}
