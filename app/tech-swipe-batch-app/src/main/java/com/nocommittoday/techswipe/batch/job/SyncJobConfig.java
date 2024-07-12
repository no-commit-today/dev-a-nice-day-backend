package com.nocommittoday.techswipe.batch.job;

import com.querydsl.core.annotations.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;

@Config
@RequiredArgsConstructor
public class SyncJobConfig {

    private static final String JOB_NAME = "syncJob";

    private final JobRepository jobRepository;

    private final Job syncImageJob;
    private final Job syncTechContentProviderJob;
    private final Job syncTechContentJob;

    @Bean(JOB_NAME)
    public Job job() {
        final JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(syncImageJobStep())
                .next(syncTechContentProviderJobStep())
                .next(syncTechContentJobStep())
                .build();
    }

    @Bean
    @JobScope
    public Step syncImageJobStep() {
        final StepBuilder stepBuilder = new StepBuilder("syncImageJobStep", jobRepository);
        return stepBuilder
                .job(syncImageJob)
                .build();
    }

    @Bean
    @JobScope
    public Step syncTechContentProviderJobStep() {
        final StepBuilder stepBuilder = new StepBuilder("syncTechContentProviderJobStep", jobRepository);
        return stepBuilder
                .job(syncTechContentProviderJob)
                .build();
    }

    @Bean
    @JobScope
    public Step syncTechContentJobStep() {
        final StepBuilder stepBuilder = new StepBuilder("syncTechContentJobStep", jobRepository);
        return stepBuilder
                .job(syncTechContentJob)
                .build();
    }
}
