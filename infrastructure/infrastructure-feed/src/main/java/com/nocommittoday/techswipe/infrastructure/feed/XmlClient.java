package com.nocommittoday.techswipe.infrastructure.feed;

import com.nocommittoday.techswipe.infrastructure.web.ClientLoggingInterceptor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class XmlClient {

    private final RestClient restClient;

    public XmlClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .requestInterceptor(new ClientLoggingInterceptor(this))
                .build();
    }

    public String get(String url) {
        return restClient.get().uri(url).retrieve().body(String.class);
    }
}
