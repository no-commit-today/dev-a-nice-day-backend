package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class SubscribedContentBuilder {

    @Nullable
    private SubscriptionId subscriptionId;

    @Nullable
    private String url;

    private boolean initialized = false;

    @Nullable
    private String title;

    @Nullable
    private String imageUrl;

    @Nullable
    private LocalDate publishedDate;

    @Nullable
    private String content;

    public SubscribedContentBuilder() {
    }

    public static SubscribedContent create() {
        return new SubscribedContentBuilder().build();
    }

    public SubscribedContentBuilder subscriptionId(SubscriptionId subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public SubscribedContentBuilder url(String url) {
        this.url = url;
        return this;
    }

    public SubscribedContentBuilder initialized(boolean initialized) {
        this.initialized = initialized;
        return this;
    }

    public SubscribedContentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SubscribedContentBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public SubscribedContentBuilder publishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public SubscribedContentBuilder content(String content) {
        this.content = content;
        return this;
    }

    public SubscribedContent build() {
        fillRequiredFields();
        return new SubscribedContent(
            subscriptionId,
            url,
            initialized,
            title,
            imageUrl,
            publishedDate,
            content
        );
    }

    private void fillRequiredFields() {
        if (subscriptionId == null) {
            subscriptionId = new SubscriptionId(LocalAutoIncrementIdUtils.nextId());
        }
        if (url == null) {
            url = "content-url-" + subscriptionId.value();
        }
    }

}
