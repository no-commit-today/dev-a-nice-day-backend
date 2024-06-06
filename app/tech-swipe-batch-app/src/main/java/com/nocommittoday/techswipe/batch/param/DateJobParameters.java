package com.nocommittoday.techswipe.batch.param;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Getter
public class DateJobParameters {

    public static final String NAME = "DateJobParameters";

    private LocalDate date;

    @Value("#{jobParameters['date']}")
    public void setType(final String dateParam) {
        this.date = LocalDate.parse(dateParam);
    }
}
