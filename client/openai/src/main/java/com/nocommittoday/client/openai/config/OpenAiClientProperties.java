package com.nocommittoday.client.openai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app.client.openai")
public record OpenAiClientProperties(
        @DefaultValue("https://api.openai.com") String baseUrl,
        String apiKey
) {
}
