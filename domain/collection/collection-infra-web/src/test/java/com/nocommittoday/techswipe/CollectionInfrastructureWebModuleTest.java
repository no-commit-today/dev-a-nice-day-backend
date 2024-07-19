package com.nocommittoday.techswipe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClient;

@SpringBootTest
@SpringBootApplication
@Import(CollectionInfrastructureWebModuleTest.Config.class)
class CollectionInfrastructureWebModuleTest {

    @Test
    void contextLoad() {
    }

    static class Config {

        @Bean
        @Scope("prototype")
        @ConditionalOnMissingBean
        public RestClient.Builder restClientBuilder() {
            return RestClient.builder();
        }
    }
}
