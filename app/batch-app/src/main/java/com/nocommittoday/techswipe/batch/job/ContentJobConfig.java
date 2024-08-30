package com.nocommittoday.techswipe.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContentJobConfig {

    private static final String JOB_NAME = "contentJob";

    private final JobRepository jobRepository;

    public ContentJobConfig(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(contentCollectJobStep(null))
                .next(collectedContentInitializeJobStep(null))
                .next(collectedContentCategorizeJobStep(null))
                .next(collectedContentSummarizeJobStep(null))
                .next(collectedContentPublishJobStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step contentCollectJobStep(Job contentCollectJob) {
        StepBuilder stepBuilder = new StepBuilder("contentCollectJobStep", jobRepository);
        return stepBuilder
                .job(contentCollectJob)
                .build();
    }

    @Bean
    @JobScope
    public Step collectedContentInitializeJobStep(Job collectedContentInitializeJob) {
        StepBuilder stepBuilder = new StepBuilder("collectedContentInitializeJobStep", jobRepository);
        return stepBuilder
                .job(collectedContentInitializeJob)
                .build();
    }

    @Bean
    @JobScope
    public Step collectedContentCategorizeJobStep(Job collectedContentCategorizeJob) {
        StepBuilder stepBuilder = new StepBuilder("collectedContentCategorizeJobStep", jobRepository);
        return stepBuilder
                .job(collectedContentCategorizeJob)
                .build();
    }

    @Bean
    @JobScope
    public Step collectedContentSummarizeJobStep(Job collectedContentSummarizeJob) {
        StepBuilder stepBuilder = new StepBuilder("collectedContentSummarizeJobStep", jobRepository);
        return stepBuilder
                .job(collectedContentSummarizeJob)
                .build();
    }

    @Bean
    @JobScope
    public Step collectedContentPublishJobStep(Job collectedContentPublishJob) {
        StepBuilder stepBuilder = new StepBuilder("collectedContentPublishJobStep", jobRepository);
        return stepBuilder
                .job(collectedContentPublishJob)
                .build();
    }
}