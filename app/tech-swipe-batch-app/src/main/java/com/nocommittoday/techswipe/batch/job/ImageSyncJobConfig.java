package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.client.ImageSyncQueryResponse;
import com.nocommittoday.techswipe.batch.client.ImageSyncQueryRestClient;
import com.nocommittoday.techswipe.batch.param.LocalDateTimeFromJobParameter;
import com.nocommittoday.techswipe.batch.param.LocalDateTimeToJobParameter;
import com.nocommittoday.techswipe.batch.reader.PagingItemReaderAdapter;
import com.nocommittoday.techswipe.batch.reader.PagingItemReaderAdapterBuilder;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
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

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ImageSyncJobConfig {

    private static final String JOB_NAME = "imageSyncJob";
    private static final String STEP_NAME = "imageSyncStep";
    private static final int CHUNK_SIZE = 100;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final EntityManagerFactory emf;

    private final ImageSyncQueryRestClient imageSyncQueryRestClient;

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .validator(jobParametersValidator())
                .start(step())
                .build();
    }

    @Bean(JOB_NAME + "JobParametersValidator")
    public JobParametersValidator jobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"from", "to"},
                new String[]{}
        );
    }

    @Bean(JOB_NAME + LocalDateTimeFromJobParameter.NAME)
    @JobScope
    public LocalDateTimeFromJobParameter fromJobParameter() {
        return new LocalDateTimeFromJobParameter();
    }

    @Bean(JOB_NAME + LocalDateTimeToJobParameter.NAME)
    @JobScope
    public LocalDateTimeToJobParameter toJobParameter() {
        return new LocalDateTimeToJobParameter();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        final StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<ImageSyncQueryResponse, ImageEntity>chunk(CHUNK_SIZE, txManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean(STEP_NAME + "ItemReader")
    @StepScope
    public PagingItemReaderAdapter<ImageSyncQueryResponse> reader() {
        final LocalDateTime from = fromJobParameter().getFrom();
        final LocalDateTime to = toJobParameter().getTo();
        return new PagingItemReaderAdapterBuilder<ImageSyncQueryResponse>()
                .pageOffset(1)
                .name(STEP_NAME + "ItemReader")
                .readStrategy((page, size) ->
                        imageSyncQueryRestClient.getList(from, to, page, size).content()
                )
                .build();
    }

    @Bean(STEP_NAME + "ItemProcessor")
    @StepScope
    public ItemProcessor<ImageSyncQueryResponse, ImageEntity> processor() {
        return item -> new ImageEntity(
                item.id(),
                item.url(),
                item.originalUrl(),
                item.storedName()
        );
    }

    @Bean(STEP_NAME + "ItemWriter")
    @StepScope
    public JpaItemWriter<ImageEntity> writer() {
        return new JpaItemWriterBuilder<ImageEntity>()
                .entityManagerFactory(emf)
                .usePersist(false)
                .build();
    }
}
