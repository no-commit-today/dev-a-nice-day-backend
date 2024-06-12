package com.nocommittoday.techswipe.collection.infrastructure;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CollectionWebConfig {

    @Bean
    public OpenAiService openAiService(
            @Value("${app.client.openai.api-key}") final String apiKey
    ) {
        return new OpenAiService(apiKey);
    }
}
