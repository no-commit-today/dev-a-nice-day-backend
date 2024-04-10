package com.nocommittoday.client.openai.config;

import com.nocommittoday.client.openai.OpenAiClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenAiClientProperties.class)
public class OpenAiClientConfig {

    @Bean
    public OpenAiClient openAiClient(
            final RestTemplateBuilder restTemplateBuilder, final OpenAiClientProperties properties
    ) {
        return new OpenAiClient(
                restTemplateBuilder, properties.baseUrl(), properties.apiKey(), properties.timeoutMillis()
        );
    }
}
