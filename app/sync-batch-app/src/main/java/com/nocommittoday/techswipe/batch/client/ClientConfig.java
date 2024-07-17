package com.nocommittoday.techswipe.batch.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    @Bean
    public SyncQueryRestClient imageSyncQueryRestClient(
            final RestClient.Builder restClientBuilder,
            @Value("${app.client.sync.base-url}") final String baseUrl,
            @Value("${app.client.sync.authorization}") final String authorization
    ) {
        return new SyncQueryRestClient(restClientBuilder, baseUrl, authorization);
    }
}
