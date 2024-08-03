package com.nocommittoday.techswipe.domain.subscription;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record SubscribedContent(
        String url,
        String title,
        @Nullable String imageUrl,
        LocalDate publishedDate,
        String content
) {
}
