package com.nocommittoday.techswipe.storage.mysql.test;

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