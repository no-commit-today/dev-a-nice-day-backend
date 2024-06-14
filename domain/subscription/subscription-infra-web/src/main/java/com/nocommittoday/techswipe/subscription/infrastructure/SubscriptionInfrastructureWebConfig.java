package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.client.feed.FeedClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeedClientConfig.class)
public class SubscriptionInfrastructureWebConfig {
}
