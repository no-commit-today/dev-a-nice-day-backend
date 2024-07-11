package com.nocommittoday.techswipe.test.storage.mysql;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@TestConfiguration
@EnableJpaAuditing
public class TestJpaConfig {

    @Bean
    public LocalAutoIncrementIdGenerator localAutoIncrementIdGenerator() {
        return new LocalAutoIncrementIdGenerator();
    }
}