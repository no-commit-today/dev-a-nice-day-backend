package com.nocommittoday.techswipe.batch.param;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class LocalDateDateJobParameter {

    public static final String NAME = "LocalDateDateJobParameter";

    public static final String PARAMETER_NAME = "date";

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    @Value("#{jobParameters['" + PARAMETER_NAME + "']}")
    public void setType(final LocalDate date) {
        this.date = date;
    }
}
