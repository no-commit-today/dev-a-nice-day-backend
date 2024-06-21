package com.nocommittoday.techswipe.collection.infrastructure;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class CollectionWebConfig {

    @Bean
    @Profile("local")
    public CollectionProcessor localCollectionProcessor() {
        return new LocalCollectionProcessor();
    }

    @Bean
    @Profile("!local")
    public CollectionProcessor openAiCollectionProcessor(
            final OpenAiService openAiService
    ) {
        return new OpenAiCollectionProcessor(openAiService);
    }

    @Bean
    @Profile("!local")
    public OpenAiService openAiService(
            @Value("${app.client.openai.api-key}") final String apiKey
    ) {
        return new OpenAiService(apiKey);
    }
}
