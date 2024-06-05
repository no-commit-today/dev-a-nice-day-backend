package com.nocommittoday.techswipe.subscription.application.port.in;

import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;

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
