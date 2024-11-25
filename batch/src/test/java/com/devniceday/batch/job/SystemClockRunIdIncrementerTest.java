package com.devniceday.batch.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class SystemClockRunIdIncrementerTest {

    private Clock clock;
    private SystemClockRunIdIncrementer incrementer;

    @Test
    void 매개변수가_null일때_RunId추가() {
        // given
        Instant now = Instant.now();
        clock = Clock.fixed(now, ZoneId.systemDefault());
        incrementer = new SystemClockRunIdIncrementer(clock);

        // when
        JobParameters nextParams = incrementer.getNext(null);

        // then
        assertThat(nextParams.getLong("run.id")).isEqualTo(now.toEpochMilli());
    }

    @Test
    void 매개변수가_비어있을때_RunId추가() {
        // given
        Instant now = Instant.now();
        clock = Clock.fixed(now, ZoneId.systemDefault());
        incrementer = new SystemClockRunIdIncrementer(clock);

        // when
        JobParameters nextParams = incrementer.getNext(new JobParameters());

        // then
        assertThat(nextParams.getLong("run.id")).isEqualTo(now.toEpochMilli());
    }

    @Test
    void 기존매개변수_유지() {
        // given
        Instant now = Instant.now();
        clock = Clock.fixed(now, ZoneId.systemDefault());
        incrementer = new SystemClockRunIdIncrementer(clock);

        JobParameters existingParams = new JobParametersBuilder()
                .addString("existing.param", "value")
                .toJobParameters();

        // when
        JobParameters nextParams = incrementer.getNext(existingParams);

        // then
        assertThat(nextParams.getString("existing.param")).isEqualTo("value");
        assertThat(nextParams.getLong("run.id")).isEqualTo(now.toEpochMilli());
    }
}