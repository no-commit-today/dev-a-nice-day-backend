package com.nocommittoday.techswipe.core.infrastructure;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateHolder {

    public LocalDate now() {
        return LocalDate.now();
    }
}
