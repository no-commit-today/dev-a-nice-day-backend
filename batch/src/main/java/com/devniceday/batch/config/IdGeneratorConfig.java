package com.devniceday.batch.config;

import com.devniceday.module.IdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class IdGeneratorConfig {

    @Bean
    public IdGenerator idGenerator(Clock clock) {
        return new IdGenerator(clock);
    }
}
