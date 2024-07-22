package com.nocommittoday.techswipe.client.feed;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeedClientConfig {

    @Bean
    public FeedClient feedClient(final XmlClient xmlClient, final SyndFeedBuilder syndFeedBuilder) {
        return new FeedClient(xmlClient, syndFeedBuilder);
    }

    @Bean
    public XmlClient xmlClient(final RestTemplateBuilder restTemplateBuilder) {
        return new XmlClient(restTemplateBuilder);
    }

    @Bean
    public SyndFeedBuilder syndFeedBuilder() {
        return new SyndFeedBuilder();
    }
}
