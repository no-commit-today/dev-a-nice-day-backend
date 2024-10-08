package com.nocommittoday.techswipe.infrastructure.jsoup;

import com.nocommittoday.techswipe.infrastructure.web.ClientLoggingInterceptor;
import com.nocommittoday.techswipe.infrastructure.web.ClientResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
public class HtmlClient {

    private final RestClient restClient;

    public HtmlClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.TEXT_HTML_VALUE)
                .requestInterceptor(new ClientLoggingInterceptor(this))
                .build();
    }

    @Retryable(retryFor = HttpServerErrorException.class)
    public ClientResponse<String> get(String url) {
        try {
            return ClientResponse.success(
                    restClient.get()
                            .uri(url)
                            .retrieve()
                            .body(String.class)
            );
        } catch (HttpClientErrorException e) {
            return ClientResponse.fail(e);
        }
    }
}
