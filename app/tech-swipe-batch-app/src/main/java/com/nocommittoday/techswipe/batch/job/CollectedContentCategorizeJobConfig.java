package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.application.PromptWithInMemoryCacheReader;
import com.nocommittoday.techswipe.batch.exception.CategorizeFailureException;
import com.nocommittoday.techswipe.batch.listener.CollectedContentCategorizeSkipListener;
import com.nocommittoday.techswipe.batch.processor.CollectedContentCategorizeProcessor;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.infrastructure.CollectionProcessor;
import com.nocommittoday.techswipe.collection.infrastructure.PromptReader;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CollectedContentCategorizeJobConfig {

    private static final String JOB_NAME = "collectedContentCategorizeJob";
    private static final String STEP_NAME = "collectedContentCategorizeStep";
    private static final int CHUNK_SIZE = 100;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final PromptReader promptReader;
    private final CollectionProcessor collectionProcessor;

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        final StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<CollectedContentEntity, CollectedContentEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())

                .faultTolerant()
                .skip(CategorizeFailureException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(listener())

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public JpaPagingItemReader<CollectedContentEntity> reader() {
        return new JpaPagingItemReaderBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .pageSize(CHUNK_SIZE)
                .queryString("""
                        select c from CollectedContentEntity c
                        join fetch c.techContentProviderEntity
                        where c.status = :status and c.deleted = false
                        """)
                .parameterValues(Map.of(
                        "status", CollectionStatus.NONE
                ))
                .build();
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public CollectedContentCategorizeProcessor processor() {
        return new CollectedContentCategorizeProcessor(
                promptWithInMemoryCacheReader(), collectionProcessor
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<CollectedContentEntity> writer() {
        return new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }

    @Bean(STEP_NAME + "PromptWithInMemoryCacheReader")
    @StepScope
    public PromptWithInMemoryCacheReader promptWithInMemoryCacheReader() {
        return new PromptWithInMemoryCacheReader(promptReader);
    }

    @Bean(STEP_NAME + "SkipListener")
    @StepScope
    public CollectedContentCategorizeSkipListener listener() {
        return new CollectedContentCategorizeSkipListener();
    }
}
