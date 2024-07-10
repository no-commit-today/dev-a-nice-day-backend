package com.nocommittoday.techswipe.core.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInfraConfig {

    @Bean
    public IdGenerator idGenerator(final SystemClockHolder systemClockHolder) {
        return new IdGenerator(systemClockHolder);
    }
}
