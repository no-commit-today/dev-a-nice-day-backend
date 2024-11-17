package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.SubscribedContent;
import com.devniceday.test.java.LongIncrementUtil;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public class SubscribedContentBuilder {

    @Nullable
    private Long subscriptionId;

    @Nullable
    private String url;

    @Nullable
    private String title;

    @Nullable
    private String imageUrl;

    @Nullable
    private LocalDate publishedDate;

    @Nullable
    private String content;


    public static SubscribedContent create() {
        return new SubscribedContentBuilder().build();
    }

    public SubscribedContentBuilder subscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public SubscribedContentBuilder url(String url) {
        this.url = url;
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
            title,
            imageUrl,
            publishedDate,
            content
        );
    }

    private void fillRequiredFields() {
        if (subscriptionId == null) {
            subscriptionId = LongIncrementUtil.next();
        }
        if (url == null) {
            url = "content-url-" + subscriptionId;
        }
    }

}
