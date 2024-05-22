package com.nocommittoday.techswipe.batch.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nocommittoday.techswipe.batch.model.TechBlogSubscriptionItem;
import com.nocommittoday.techswipe.batch.model.TechPostItem;
import com.nocommittoday.techswipe.batch.param.DateJobParameters;
import com.nocommittoday.techswipe.batch.service.TechBlogUrlToIdMapper;
import com.nocommittoday.techswipe.batch.service.TechPostSubscribeService;
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
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TechBlogSubscribeJobConfig {

    private static final String JOB_NAME = "techBlogSubscribeJob";
    private static final String STEP_NAME = "techBlogSubscribeStep";
    private static final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final DateJobParameters dateJobParameters;
    private final TechBlogUrlToIdMapper techBlogUrlToIdMapper;
    private final TechPostSubscribeService techPostSubscribeService;

    @Bean
    public Job techBlogSubscribeJob() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .start(techBlogSubscribeStep())
                .validator(techBlogSubscribeJobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public JobParametersValidator techBlogSubscribeJobParametersValidator() {
        return new DefaultJobParametersValidator(
                new String[]{"date"},
                new String[]{}
        );
    }

    @Bean
    public Step techBlogSubscribeStep() {
        final StepBuilder stepBuilder = new StepBuilder(STEP_NAME, jobRepository);
        return stepBuilder
                .<TechBlogSubscriptionItem, List<TechPostItem>>chunk(CHUNK_SIZE, txManager)
                .reader(techBlogSubscribeJobItemReader())
                .processor(techBlogSubscriptionItemProcessor())
                .writer(techBlogSubscriptionItemWriter())
                .build();
    }

    @Bean
    @JobScope
    public DateJobParameters dateJobParameters() {
        return new DateJobParameters();
    }

    @Bean
    @StepScope
    public JsonItemReader<TechBlogSubscriptionItem> techBlogSubscribeJobItemReader() {
        final JsonObjectReader<TechBlogSubscriptionItem> jsonObjectReader = new JacksonJsonObjectReader<>(
                TechBlogSubscriptionItem.class);
        return new JsonItemReaderBuilder<TechBlogSubscriptionItem>()
                .name("techBlogSubscribeJobItemReader")
                .resource(new ClassPathResource("tech-blog-subscription.json"))
                .jsonObjectReader(jsonObjectReader)
                .build();
    }

    @Bean
    @StepScope
    public TechBlogSubscribeJobItemProcessor techBlogSubscriptionItemProcessor() {
        return new TechBlogSubscribeJobItemProcessor(techBlogUrlToIdMapper, techPostSubscribeService);
    }

    @Bean
    @StepScope
    public JsonFileListItemWriter<TechPostItem> techBlogSubscriptionItemWriter() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new JsonFileListItemWriter<>(
                new FileSystemResource(dateJobParameters.getDate() + ".json"),
                new JacksonJsonObjectMarshaller<>(objectMapper)
        );
    }
}
