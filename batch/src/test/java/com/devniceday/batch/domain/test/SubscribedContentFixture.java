package com.devniceday.batch.domain.test;

import com.devniceday.test.java.LongIncrementUtil;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public class SubscribedContentFixture {

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


    public static com.devniceday.batch.domain.SubscribedContent create() {
        return new SubscribedContentFixture().build();
    }

    public SubscribedContentFixture subscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public SubscribedContentFixture url(String url) {
        this.url = url;
        return this;
    }

    public SubscribedContentFixture title(String title) {
        this.title = title;
        return this;
    }

    public SubscribedContentFixture imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public SubscribedContentFixture publishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public SubscribedContentFixture content(String content) {
        this.content = content;
        return this;
    }

    public com.devniceday.batch.domain.SubscribedContent build() {
        fillRequiredFields();
        return new com.devniceday.batch.domain.SubscribedContent(
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
