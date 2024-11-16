package com.devniceday.batch.domain;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record SubscribedContent(
        long subscriptionId,
        String url,
        @Nullable String title,
        @Nullable String imageUrl,
        @Nullable LocalDate publishedDate,
        @Nullable String content
) {
}
