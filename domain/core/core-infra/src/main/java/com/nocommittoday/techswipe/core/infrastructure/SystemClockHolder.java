package com.nocommittoday.techswipe.core.infrastructure;

import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder {

    private final Clock clock = Clock.systemUTC();

    public long millis() {
        return clock.millis();
    }
}
