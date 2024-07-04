package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.application.CollectedContentUrlInMemoryExistsReader;
import com.nocommittoday.techswipe.batch.param.TechContentProviderIdJobParameters;
import com.nocommittoday.techswipe.batch.param.ProviderIdListJobParameters;
import com.nocommittoday.techswipe.batch.processor.ContentCollectProviderInitialJobItemProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.writer.JpaItemListWriter;
import com.nocommittoday.techswipe.collection.infrastructure.CollectedContentUrlListReader;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

import static com.nocommittoday.techswipe.subscription.storage.mysql.QSubscriptionEntity.subscriptionEntity;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ContentCollectProviderInitialJobConfig {

    private static final String JOB_NAME = "contentCollectProviderInitialJob";
    private static final String STEP_NAME = "contentCollectProviderInitialStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager txManager;

    private final EntityManagerFactory emf;

    private final CollectedContentUrlListReader collectedContentUrlListReader;

    private final SubscribedContentListQueryService subscribedContentListQueryService;

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .validator(jobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
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
                        providerIdIn(),
                        subscriptionEntity.deleted.isFalse()
                ).orderBy(subscriptionEntity.id.asc())
        );
        return reader;
    }

    private BooleanExpression providerIdIn() {
        return Optional.ofNullable(providerIdListJobParameters().getProviderIdList())
                .map(idList -> idList.stream()
                        .mapToLong(TechContentProvider.Id::value)
                        .boxed()
                        .toList()
                ).map(subscriptionEntity.provider.id::in)
                .orElse(null);
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ContentCollectProviderInitialJobItemProcessor processor() {
        return new ContentCollectProviderInitialJobItemProcessor(
                subscribedContentListQueryService,
                collectedContentUrlInMemoryExistsReader()
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

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"provider.ids"},
                new String[]{}
        );
    }

    @Bean(JOB_NAME + TechContentProviderIdJobParameters.NAME)
    @JobScope
    public ProviderIdListJobParameters providerIdListJobParameters() {
        return new ProviderIdListJobParameters();
    }

    @Bean
    @StepScope
    public CollectedContentUrlInMemoryExistsReader collectedContentUrlInMemoryExistsReader() {
        return new CollectedContentUrlInMemoryExistsReader(
                collectedContentUrlListReader,
                providerIdListJobParameters().getProviderIdList()
        );
    }

}
