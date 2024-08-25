package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.domain.subscription.BatchSubscribedContentInitializerDelegator;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.reader.QuerydslZeroPagingItemReader;
import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.storage.mysql.batch.BatchCollectedContentEntityMapper;
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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.nocommittoday.techswipe.storage.mysql.collection.QCollectedContentEntity.collectedContentEntity;

@Configuration
public class CollectedContentInitializeJobConfig {

    private static final String JOB_NAME = "collectedContentInitializeJob";
    private static final String STEP_NAME = "collectedContentInitializeStep";
    private static final int CHUNK_SIZE = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final BatchSubscribedContentInitializerDelegator contentInitializer;
    private final BatchCollectedContentEntityMapper collectedContentEntityMapper;

    public CollectedContentInitializeJobConfig(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            EntityManagerFactory emf,
            BatchSubscribedContentInitializerDelegator contentInitializer,
            BatchCollectedContentEntityMapper collectedContentEntityMapper
    ) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.emf = emf;
        this.contentInitializer = contentInitializer;
        this.collectedContentEntityMapper = collectedContentEntityMapper;
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

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslPagingItemReader<CollectedContentEntity> reader() {
        QuerydslZeroPagingItemReader<CollectedContentEntity> reader = new QuerydslZeroPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(collectedContentEntity)
                .join(collectedContentEntity.subscription).fetchJoin()
                .where(
                        collectedContentEntity.status.eq(CollectionStatus.COLLECTED),
                        collectedContentEntity.deleted.isFalse()
                )
                .orderBy(collectedContentEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<CollectedContentEntity, CollectedContentEntity> processor() {
        return entity -> {
            CollectedContent collectedContent = entity.toDomain();
            Subscription subscription = entity.getSubscription().toDomain();
            SubscribedContent subscribedContent = new SubscribedContent(
                    subscription.getId(),
                    collectedContent.getUrl(),
                    false,
                    collectedContent.getTitle(),
                    collectedContent.getImageUrl(),
                    collectedContent.getPublishedDate(),
                    collectedContent.getContent()
            );
            SubscribedContent initializedSubscribedContent = contentInitializer
                    .initialize(subscription, subscribedContent);

            CollectedContent initializedCollectedContent = collectedContent.initialize(
                    initializedSubscribedContent.getTitle(),
                    initializedSubscribedContent.getPublishedDate(),
                    initializedSubscribedContent.getContent(),
                    initializedSubscribedContent.getImageUrl()
            );
            return collectedContentEntityMapper.from(
                    initializedCollectedContent
            );
        };
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<CollectedContentEntity> writer() {
        return new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }
}
