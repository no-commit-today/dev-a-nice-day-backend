package com.devniceday.batch.domain.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class OpenAiConfig {

    @Configuration
    @ConditionalOnProperty(name = "app.collection.openai.enabled", havingValue = "true")
    static class OpenAi {

        @Bean
        CategorizationClient categorizationClientOpenAi(
                ChatClient.Builder chatClientBuilder,
                @Value("${app.collection.openai.categorization-model}") String model
        ) {
            return new CategorizationClientOpenAi(chatClientBuilder, model);
        }

    }

    @Configuration
    @ConditionalOnMissingBean(OpenAi.class)
    static class Local {

        @Bean
        CategorizationClient categorizationClientLocal() {
            return new CategorizationClientLocal();
        }
    }

    private OpenAiConfig() {
    }
}
