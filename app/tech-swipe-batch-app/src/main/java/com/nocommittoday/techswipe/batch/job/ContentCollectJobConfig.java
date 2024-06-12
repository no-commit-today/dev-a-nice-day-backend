package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.param.DateJobParameters;
import com.nocommittoday.techswipe.batch.processor.ContentCollectJobItemProcessor;
import com.nocommittoday.techswipe.batch.writer.JpaItemListWriter;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.service.SubscribedContentListQueryService;
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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ContentCollectJobConfig {

    private static final String JOB_NAME = "contentCollectJob";
    private static final String STEP_NAME = "contentCollectStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

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
                .<TechContentProviderEntity, List<CollectedContentEntity>>chunk(CHUNK_SIZE, txManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public JpaPagingItemReader<TechContentProviderEntity> itemReader() {
        return new JpaPagingItemReaderBuilder<TechContentProviderEntity>()
                .name(STEP_NAME + "ItemReader")
                .entityManagerFactory(emf)
                .pageSize(CHUNK_SIZE)
                .queryString("""
                        select p from TechContentProviderEntity p
                        join SubscriptionEntity s on p.id = s.providerId
                        where p.deleted = false
                        """)
                .build();
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<TechContentProviderEntity, List<CollectedContentEntity>> itemProcessor() {
        return new ContentCollectJobItemProcessor(subscribedContentListQueryService, dateJobParameters());
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemListWriter<CollectedContentEntity> itemWriter() {
        final JpaItemWriter<CollectedContentEntity> jpaItemWriter = new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(true)
                .build();
        return new JpaItemListWriter<>(jpaItemWriter);
    }
}
