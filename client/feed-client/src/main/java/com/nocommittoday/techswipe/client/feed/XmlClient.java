package com.nocommittoday.techswipe.client.feed;

import com.nocommittoday.techswipe.client.core.ClientLog;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class XmlClient {

    private final RestTemplate restTemplate;

    public XmlClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @ClientLog
    public String get(final String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
