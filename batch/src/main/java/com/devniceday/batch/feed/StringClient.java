package com.devniceday.batch.feed;

import com.devniceday.module.timetracer.TimeTrace;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
public class StringClient {

    private final RestClient restClient;

    public StringClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .build();
    }

    @Retryable(retryFor = HttpServerErrorException.class)
    @TimeTrace
    public String get(String url) {
        return restClient.get().uri(url).retrieve().body(String.class);
    }
}
