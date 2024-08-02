package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.infrastructure.CollectedUrlFilterCreator;
import com.nocommittoday.techswipe.batch.listener.SubscriptionFailureSkipListener;
import com.nocommittoday.techswipe.batch.param.TechContentProviderIdJobParameter;
import com.nocommittoday.techswipe.batch.processor.ContentCollectProviderInitialJobItemProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.writer.JpaItemListWriter;
import com.nocommittoday.techswipe.domain.collection.CollectedContentIdGenerator;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntityMapper;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionSubscribeFailureException;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
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
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

import static com.nocommittoday.techswipe.storage.mysql.subscription.QSubscriptionEntity.subscriptionEntity;

@Configuration
public class ContentCollectProviderInitialJobConfig {

    private static final String JOB_NAME = "contentCollectProviderInitialJob";
    private static final String STEP_NAME = "contentCollectProviderInitialStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final CollectedUrlFilterCreator collectedUrlFilterCreator;
    private final SubscribedContentListQueryService subscribedContentListQueryService;
    private final CollectedContentIdGenerator collectedContentIdGenerator;
    private final CollectedContentEntityMapper collectedContentEntityMapper;

    public ContentCollectProviderInitialJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            CollectedUrlFilterCreator collectedUrlFilterCreator,
            SubscribedContentListQueryService subscribedContentListQueryService,
            CollectedContentIdGenerator collectedContentIdGenerator,
            CollectedContentEntityMapper collectedContentEntityMapper
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.collectedUrlFilterCreator = collectedUrlFilterCreator;
        this.subscribedContentListQueryService = subscribedContentListQueryService;
        this.collectedContentIdGenerator = collectedContentIdGenerator;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
    }

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(jobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"provider.id"},
                new String[]{}
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
        final StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<SubscriptionEntity, List<CollectedContentEntity>>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())

                .faultTolerant()
                .skip(SubscriptionSubscribeFailureException.class)
                .listener(listener())

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<SubscriptionEntity> reader() {
        final QuerydslPagingItemReader<SubscriptionEntity> reader = new QuerydslPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(subscriptionEntity)
                .where(
                        subscriptionEntity.provider.id.eq(providerIdListJobParameter().getProviderId().value()),
                        subscriptionEntity.deleted.isFalse()
                ).orderBy(subscriptionEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ContentCollectProviderInitialJobItemProcessor processor() {
        return new ContentCollectProviderInitialJobItemProcessor(
                subscribedContentListQueryService,
                collectedContentIdGenerator,
                collectedUrlFilterCreator,
                collectedContentEntityMapper
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemListWriter<CollectedContentEntity> writer() {
        final JpaItemWriter<CollectedContentEntity> jpaItemWriter = new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
        return new JpaItemListWriter<>(jpaItemWriter);
    }

    @Bean(STEP_NAME + "SubscriptionFailureSkipListener")
    @StepScope
    public SubscriptionFailureSkipListener listener() {
        return new SubscriptionFailureSkipListener();
    }

}
