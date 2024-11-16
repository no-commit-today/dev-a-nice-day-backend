package com.devniceday.batch.job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.time.Clock;

public class SystemClockRunIdIncrementer implements JobParametersIncrementer {

    private static final String RUN_ID_KEY = "run.id";

    private final Clock clock;

    public SystemClockRunIdIncrementer(Clock clock) {
        this.clock = clock;
    }

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;

        return new JobParametersBuilder(params)
                .addLong(RUN_ID_KEY, clock.millis())
                .toJobParameters();
    }
}
