package com.nocommittoday.techswipe.domain.subscription;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class SubscribedContent {

    private final SubscriptionId subscriptionId;

    private final String url;

    private final boolean initialized;

    @Nullable
    private final String title;

    @Nullable
    private final String imageUrl;

    @Nullable
    private final LocalDate publishedDate;

    @Nullable
    private final String content;

    public SubscribedContent(
            SubscriptionId subscriptionId,
            String url,
            boolean initialized,
            @Nullable String title,
            @Nullable String imageUrl,
            @Nullable LocalDate publishedDate,
            @Nullable String content
    ) {
        this.subscriptionId = subscriptionId;
        this.url = url;
        this.initialized = initialized;
        this.title = title;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.content = content;
    }

    public SubscribedContent initialize(
            @Nullable String title,
            @Nullable String imageUrl,
            @Nullable LocalDate publishedDate,
            @Nullable String content
    ) {
        SubscribedContent initializedContent = new SubscribedContent(
                this.subscriptionId,
                this.url,
                true,
                title != null ? title : this.title,
                imageUrl != null ? imageUrl : this.imageUrl,
                publishedDate != null ? publishedDate : this.publishedDate,
                content != null ? content : this.content
        );

        if (initializedContent.getTitle() == null) {
            throw new IllegalStateException("초기화된 컨텐츠의 제목은 null이 될 수 없습니다.");
        }
        if (initializedContent.getPublishedDate() == null) {
            throw new IllegalStateException("초기화된 컨텐츠의 발행일은 null이 될 수 없습니다.");
        }
        if (initializedContent.getContent() == null) {
            throw new IllegalStateException("초기화된 컨텐츠의 내용은 null이 될 수 없습니다.");
        }

        return initializedContent;
    }

    public SubscriptionId getSubscriptionId() {
        return subscriptionId;
    }

    public String getUrl() {
        return url;
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Nullable
    public String getTitle() {
        if (initialized && title == null) {
            throw new IllegalStateException("초기화된 컨텐츠의 제목은 null이 될 수 없습니다.");
        }
        return title;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public LocalDate getPublishedDate() {
        if (initialized && publishedDate == null) {
            throw new IllegalStateException("초기화된 컨텐츠의 발행일은 null이 될 수 없습니다.");
        }
        return publishedDate;
    }

    @Nullable
    public String getContent() {
        if (initialized && content == null) {
            throw new IllegalStateException("초기화된 컨텐츠의 내용은 null이 될 수 없습니다.");
        }
        return content;
    }
}
