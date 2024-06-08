package com.nocommittoday.techswipe.collection.adapter.out.web;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
class CollectionWebConfig {

    @Bean
    public OpenAiService openAiService(
            @Value("${app.client.openai.api-key}") final String apiKey
    ) {
        return new OpenAiService(apiKey);
    }
}
