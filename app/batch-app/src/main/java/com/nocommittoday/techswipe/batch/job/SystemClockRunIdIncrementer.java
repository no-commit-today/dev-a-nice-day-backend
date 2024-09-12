package com.nocommittoday.techswipe.batch.job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class SystemClockRunIdIncrementer implements JobParametersIncrementer {

    private static final String RUN_ID_KEY = "run.id";

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = (parameters == null) ? new JobParameters() : parameters;

        return new JobParametersBuilder(params)
                .addLong(RUN_ID_KEY, System.currentTimeMillis())
                .toJobParameters();
    }
}
