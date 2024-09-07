package com.nocommittoday.techswipe.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class HealthCheckJobConfig {

    private static final Logger log = LoggerFactory.getLogger(HealthCheckJobConfig.class);

    private static final String JOB_NAME = "healthCheckJob";
    private static final String STEP_NAME = "healthCheckStep";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    public HealthCheckJobConfig(JobRepository jobRepository, PlatformTransactionManager txManager) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
    }

    @Bean(JOB_NAME)
    public Job job() {
        JobBuilder jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
        return jobBuilder
                .incrementer(new SystemClockRunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean(STEP_NAME)
    @JobScope
    public Step step() {
        TaskletStepBuilder taskletStepBuilder = new TaskletStepBuilder(new StepBuilder(STEP_NAME, jobRepository));
        return taskletStepBuilder
                .tasklet((contribution, chunkContext) -> {
                    log.info("<<<<<<<<<<<< 헬스체크 >>>>>>>>>>>>");
                    return RepeatStatus.FINISHED;
                }, txManager)
                .build();
    }

}
