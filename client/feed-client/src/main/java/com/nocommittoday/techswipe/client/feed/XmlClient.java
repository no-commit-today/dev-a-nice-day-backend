package com.nocommittoday.techswipe.client.feed;

import com.nocommittoday.techswipe.client.core.ClientLoggingInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class XmlClient {

    private final RestClient restClient;

    public XmlClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .requestInterceptor(new ClientLoggingInterceptor(this))
                .build();
    }

    public String get(String url) {
        return restClient.get().uri(url).retrieve().body(String.class);
    }
}
