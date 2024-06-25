package com.nocommittoday.techswipe.subscription.domain;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record SubscribedContentResult(
        boolean success,
        Content content,
        @Nullable Exception ex
) {

    public static SubscribedContentResult ok(
            final Content content
    ) {
        return new SubscribedContentResult(true, content, null);
    }

    public static SubscribedContentResult fail(
            final String url,
            final Exception ex
    ) {
        return new SubscribedContentResult(
                false,
                new Content(
                        url,
                        null,
                        null,
                        null,
                        null

                ),
                ex
        );
    }

    public record Content(
            String url,
            String title,
            @Nullable String imageUrl,
            LocalDate publishedDate,
            String content
    ) {
    }
}
