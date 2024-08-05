package com.nocommittoday.techswipe.batch.param;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class LocalDateDateJobParameter {

    public static final String NAME = "LocalDateDateJobParameter";

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    @Value("#{jobParameters['date']}")
    public void setType(LocalDate date) {
        this.date = date;
    }
}
