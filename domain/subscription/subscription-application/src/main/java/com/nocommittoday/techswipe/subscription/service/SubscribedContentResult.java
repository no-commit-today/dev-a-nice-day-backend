package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record SubscribedContentResult(
        SubscriptionType type,
        String url,
        String title,
        @Nullable String imageUrl,
        LocalDate publishedDate,
        String content
) {
}
