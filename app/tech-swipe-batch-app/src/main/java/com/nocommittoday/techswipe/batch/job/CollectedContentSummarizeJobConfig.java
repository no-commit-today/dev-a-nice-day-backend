package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.exception.SummarizeFailureException;
import com.nocommittoday.techswipe.batch.listener.CollectedContentSummarizeSkipListener;
import com.nocommittoday.techswipe.batch.processor.CollectedContentSummarizeProcessor;
import com.nocommittoday.techswipe.batch.reader.QuerydslZeroPagingItemReader;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.infrastructure.SummarizationProcessor;
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
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Future;

import static com.nocommittoday.techswipe.collection.storage.mysql.QCollectedContentEntity.collectedContentEntity;

@Configuration
@RequiredArgsConstructor
public class CollectedContentSummarizeJobConfig {

    private static final String JOB_NAME = "collectedContentSummarizeJob";
    private static final String STEP_NAME = "collectedContentSummarizeStep";
    private static final int CHUNK_SIZE = 100;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final SummarizationProcessor summarizationProcessor;

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
                .<CollectedContentEntity, Future<CollectedContentEntity>>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(asyncProcessor())
                .writer(asyncWriter())

                .faultTolerant()
                .skip(SummarizeFailureException.class)
                .skipLimit(10)
                .listener(listener())

                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public QuerydslZeroPagingItemReader<CollectedContentEntity> reader() {
        final QuerydslZeroPagingItemReader<CollectedContentEntity> reader = new QuerydslZeroPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setPageSize(CHUNK_SIZE);
        reader.setTransacted(false);
        reader.setQueryFunction(queryFactory -> queryFactory
                .selectFrom(collectedContentEntity)
                .join(collectedContentEntity.provider).fetchJoin()
                .where(
                        collectedContentEntity.status.eq(CollectionStatus.CATEGORIZED),
                        collectedContentEntity.deleted.isFalse()
                ).orderBy(collectedContentEntity.id.asc())
        );
        return reader;
    }

    @Bean(STEP_NAME + "AsyncItemProcessor")
    @StepScope
    public AsyncItemProcessor<CollectedContentEntity, CollectedContentEntity> asyncProcessor() {
        final AsyncItemProcessor<CollectedContentEntity, CollectedContentEntity> asyncItemProcessor
                = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(processor());
        asyncItemProcessor.setTaskExecutor(taskExecutor());
        return asyncItemProcessor;
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public CollectedContentSummarizeProcessor processor() {
        return new CollectedContentSummarizeProcessor(summarizationProcessor);
    }

    @Bean(STEP_NAME + "AsyncItemWriter")
    @StepScope
    public AsyncItemWriter<CollectedContentEntity> asyncWriter() {
        final AsyncItemWriter<CollectedContentEntity> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(writer());
        return asyncItemWriter;
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<CollectedContentEntity> writer() {
        return new JpaItemWriterBuilder<CollectedContentEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }


    @Bean(STEP_NAME + "SkipListener")
    @StepScope
    public CollectedContentSummarizeSkipListener listener() {
        return new CollectedContentSummarizeSkipListener();
    }

    @Bean(JOB_NAME + "TaskExecutor")
    @JobScope
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix(JOB_NAME + "-");
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
