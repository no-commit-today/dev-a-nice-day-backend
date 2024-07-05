package com.nocommittoday.techswipe.collection.infrastructure;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CollectionInfraWebConfig {

    @Configuration
    @ConditionalOnProperty(name = "app.collection.openai.enabled", havingValue = "true")
    public static class OpenAi {

        @Bean
        public CollectionProcessor openAiCollectionProcessor(
                final OpenAiService openAiService,
                @Value("${app.collection.openai.categorization-model}") final String categorizationModel
        ) {
            return new OpenAiCollectionProcessor(openAiService, categorizationModel);
        }

        @Bean
        public OpenAiService openAiService(
                @Value("${app.collection.openai.api-key}") final String apiKey
        ) {
            return new OpenAiService(apiKey);
        }

    }
    @Configuration
    @ConditionalOnMissingBean(OpenAi.class)
    public static class Local {

        @Bean
        public CollectionProcessor localCollectionProcessor() {
            return new LocalCollectionProcessor();
        }

    }
    private CollectionInfraWebConfig() {
    }
}
