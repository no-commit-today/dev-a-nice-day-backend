package com.nocommittoday.techswipe.subscription.service;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record SubscribedContentResult(
        String url,
        String title,
        @Nullable String imageUrl,
        LocalDate publishedDate,
        String content
) {
}
