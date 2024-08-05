package com.nocommittoday.techswipe.storage.mysql.test;

import jakarta.persistence.EntityManager;
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

    @Bean
    public EntityAppender entityAppender(EntityManager em) {
        return new EntityAppender(em);
    }
}