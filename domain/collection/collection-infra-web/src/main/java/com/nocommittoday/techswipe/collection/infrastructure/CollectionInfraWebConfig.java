package com.nocommittoday.techswipe.collection.infrastructure;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class CollectionInfraWebConfig {

    @Configuration
    @ConditionalOnProperty(name = "app.collection.openai.enabled", havingValue = "true")
    static class OpenAi {

        @Bean
        SummarizationClient summarizationClientOpenAi(
                final ChatClient.Builder chatClientBuilder
        ) {
            return new SummarizationClientOpenAi(chatClientBuilder);
        }

        @Bean
        CategorizationClient categorizationClientOpenAi(
                final ChatClient.Builder chatClientBuilder,
                @Value("${app.collection.openai.categorization-model}") final String model
        ) {
            return new CategorizationClientOpenAi(chatClientBuilder, model);
        }

    }

    @Configuration
    @ConditionalOnMissingBean(OpenAi.class)
    static class Local {

        @Bean
        SummarizationClient summarizationClientLocal() {
            return new SummarizationClientLocal();
        }

        @Bean
        CategorizationClient categorizationClientLocal() {
            return new CategorizationClientLocal();
        }
    }

    private CollectionInfraWebConfig() {
    }
}
