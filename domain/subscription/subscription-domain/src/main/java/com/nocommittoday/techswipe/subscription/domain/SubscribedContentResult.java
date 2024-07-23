package com.nocommittoday.techswipe.subscription.domain;

import javax.annotation.Nullable;

public record SubscribedContentResult(
        boolean success,
        SubscribedContent content,
        @Nullable Exception exception
) {

    public static SubscribedContentResult ok(
            final SubscribedContent content
    ) {
        return new SubscribedContentResult(true, content, null);
    }

    public static SubscribedContentResult fail(
            final String url,
            final Exception ex
    ) {
        return new SubscribedContentResult(
                false,
                new SubscribedContent(
                        url,
                        null,
                        null,
                        null,
                        null

                ),
                ex
        );
    }

}
