package com.nocommittoday.techswipe.subscription.application.port.vo;

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
