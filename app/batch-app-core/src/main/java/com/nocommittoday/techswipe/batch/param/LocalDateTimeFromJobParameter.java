package com.nocommittoday.techswipe.batch.param;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public class LocalDateTimeFromJobParameter {

    public static final String NAME = "LocalDateTimeFromJobParameter";

    private LocalDateTime from;

    public LocalDateTime getFrom() {
        return from;
    }

    @Value("#{jobParameters['from']}")
    public void setFrom(LocalDateTime from) {
        this.from = from;
    }
}
