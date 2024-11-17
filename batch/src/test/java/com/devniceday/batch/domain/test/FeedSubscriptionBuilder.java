package com.devniceday.batch.domain.test;


import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.FeedSubscription;
import com.devniceday.test.java.LongIncrementUtil;

import javax.annotation.Nullable;

public class FeedSubscriptionBuilder {

    @Nullable
    private String url;

    @Nullable
    private ContentScrapping contentScrapping;

    public static FeedSubscription create() {
        return new FeedSubscriptionBuilder().build();
    }

    public FeedSubscriptionBuilder url(String url) {
        this.url = url;
        return this;
    }

    public FeedSubscriptionBuilder contentScrapping(ContentScrapping contentScrapping) {
        this.contentScrapping = contentScrapping;
        return this;
    }

    public FeedSubscription build() {
        fillRequiredFields();
        return new FeedSubscription(
                url,
                contentScrapping
        );
    }

    private void fillRequiredFields() {
        if (url == null) {
            url = "url-" + LongIncrementUtil.next();
        }
        if (contentScrapping == null) {
            contentScrapping = ContentScrappingBuilder.create();
        }
    }
}
