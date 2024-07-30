package com.nocommittoday.techswipe.batch.param;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public class LocalDateTimeToJobParameter {

    public static final String NAME = "LocalDateTimeToJobParameter";

    private LocalDateTime to;

    public LocalDateTime getTo() {
        return to;
    }

    @Value("#{jobParameters['to']}")
    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
