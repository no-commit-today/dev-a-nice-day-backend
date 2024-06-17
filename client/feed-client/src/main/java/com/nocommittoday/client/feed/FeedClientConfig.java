package com.nocommittoday.client.feed;

import com.nocommittoday.client.core.ClientLogAspect;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ClientLogAspect.class)
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
