package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;
import java.time.LocalDate;

class CollectedContentBuilder {

    private CollectedContentId id;
    private CollectionStatus status;
    @Nullable private CollectionCategoryList categoryList;
    @Nullable private Summary summary;
    private TechContentProviderId providerId;
    private SubscriptionId subscriptionId;
    @Nullable private TechContentId publishedContentId;
    private String url;
    @Nullable private String title;
    @Nullable private LocalDate publishedDate;
    @Nullable private String content;
    @Nullable private String imageUrl;

    private CollectedContentBuilder(
            CollectedContentId id,
            CollectionStatus status,
            @Nullable CollectionCategoryList categoryList,
            @Nullable Summary summary,
            TechContentProviderId providerId,
            SubscriptionId subscriptionId,
            @Nullable TechContentId publishedContentId,
            String url,
            @Nullable String title,
            @Nullable LocalDate publishedDate,
            @Nullable String content,
            @Nullable String imageUrl
    ) {
        this.id = id;
        this.status = status;
        this.categoryList = categoryList;
        this.summary = summary;
        this.providerId = providerId;
        this.subscriptionId = subscriptionId;
        this.publishedContentId = publishedContentId;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static CollectedContentBuilder from(CollectedContent content) {
        return new CollectedContentBuilder(
                content.getId(),
                content.getStatus(),
                content.getCategoryList(),
                content.getSummary(),
                content.getProviderId(),
                content.getSubscriptionId(),
                content.getPublishedContentId(),
                content.getUrl(),
                content.getTitle(),
                content.getPublishedDate(),
                content.getContent(),
                content.getImageUrl()
        );
    }

    CollectedContentBuilder status(CollectionStatus status) {
        this.status = status;
        return this;
    }

    CollectedContentBuilder categoryList(CollectionCategoryList categoryList) {
        this.categoryList = categoryList;
        return this;
    }

    CollectedContentBuilder summary(Summary summary) {
        this.summary = summary;
        return this;
    }

    CollectedContentBuilder publishedContent(TechContentId publishedContentId) {
        this.publishedContentId = publishedContentId;
        return this;
    }

    CollectedContentBuilder title(String title) {
        this.title = title;
        return this;
    }

    CollectedContentBuilder publishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    CollectedContentBuilder content(String content) {
        this.content = content;
        return this;
    }

    CollectedContentBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    CollectedContent build() {
        return new CollectedContent(
                id,
                status,
                categoryList,
                summary,
                providerId,
                subscriptionId,
                publishedContentId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }
}
