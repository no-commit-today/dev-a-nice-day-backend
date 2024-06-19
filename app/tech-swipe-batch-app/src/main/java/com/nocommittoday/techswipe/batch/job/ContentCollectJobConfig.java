package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.param.DateJobParameters;
import com.nocommittoday.techswipe.batch.processor.ContentCollectJobItemProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslPagingItemReader;
import com.nocommittoday.techswipe.batch.writer.JpaItemListWriter;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionListReader;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
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

import static com.nocommittoday.techswipe.subscription.storage.mysql.QSubscriptionEntity.subscriptionEntity;

@Configuration
@RequiredArgsConstructor
public class ContentCollectJobConfig {

    private static final String JOB_NAME = "contentCollectJob";
    private static final String STEP_NAME = "contentCollectStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final SubscriptionListReader subscriptionListReader;
    private final SubscribedContentListQueryService subscribedContentListQueryService;

    @Bean(JOB_NAME + DateJobParameters.NAME)
    @JobScope
    public DateJobParameters dateJobParameters() {
        return new DateJobParameters();
    }

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"date"},
                new String[]{}
        );
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
                        subscriptionEntity.deleted.isFalse()
                )
        );
        return reader;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<SubscriptionEntity, List<CollectedContentEntity>> processor() {
        return new ContentCollectJobItemProcessor(subscribedContentListQueryService, dateJobParameters().getDate());
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
}
