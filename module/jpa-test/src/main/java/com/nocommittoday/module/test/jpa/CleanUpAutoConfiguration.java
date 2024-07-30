package com.nocommittoday.module.test.jpa;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
public class CleanUpAutoConfiguration {

    @Bean
    public CleanUp cleanUp(JdbcTemplate jdbcTemplate, EntityManager em) {
        return new CleanUp(jdbcTemplate, em);
    }
}
