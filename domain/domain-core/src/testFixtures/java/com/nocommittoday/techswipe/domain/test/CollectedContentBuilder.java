package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class CollectedContentBuilder {

    @Nullable
    private CollectedContentId id;
    @Nullable
    private CollectionStatus status;
    @Nullable
    private CollectionCategoryList categoryList;
    @Nullable
    private Summary summary;
    @Nullable
    private TechContentProviderId providerId;
    @Nullable
    private SubscriptionId subscriptionId;
    @Nullable
    private String url;
    @Nullable
    private String title;
    @Nullable
    private LocalDate publishedDate;
    @Nullable
    private String content;
    @Nullable
    private String imageUrl;

    public CollectedContentBuilder() {
    }

    public static CollectedContent createCollected() {
        CollectedContent content = new CollectedContentBuilder().build();
        return CollectedContent.collect(
                content.getId(),
                false,
                content.getProviderId(),
                content.getSubscriptionId(),
                content.getUrl(),
                content.getTitle(),
                content.getPublishedDate(),
                content.getContent(),
                content.getImageUrl()
        );
    }

    public static CollectedContent createInit() {
        CollectedContent collected = createCollected();
        long fieldId = collected.getId().value();
        return collected.initialize(
                "title-" + fieldId,
                LocalDate.of(2021, 1, 1),
                "content-" + fieldId,
                "imageUrl-" + fieldId
        );
    }

    public static CollectedContent createCategorized() {
        CollectedContent init = createInit();
        return init.categorize(
                CollectionCategoryListBuilder.create()
        );
    }

    public static CollectedContent createSummarized() {
        CollectedContent categorized = createCategorized();
        return categorized.summarize(
                SummaryBuilder.create()
        );
    }

    public static CollectedContent createPublished() {
        CollectedContent summarized = createSummarized();
        return summarized.published();
    }

    public CollectedContentBuilder id(CollectedContentId id) {
        this.id = id;
        return this;
    }

    public CollectedContentBuilder status(CollectionStatus status) {
        this.status = status;
        return this;
    }

    public CollectedContentBuilder categoryList(CollectionCategoryList categoryList) {
        this.categoryList = categoryList;
        return this;
    }

    public CollectedContentBuilder summary(Summary summary) {
        this.summary = summary;
        return this;
    }

    public CollectedContentBuilder providerId(TechContentProviderId providerId) {
        this.providerId = providerId;
        return this;
    }

    public CollectedContentBuilder subscriptionId(SubscriptionId subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public CollectedContentBuilder url(String url) {
        this.url = url;
        return this;
    }

    public CollectedContentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CollectedContentBuilder publishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public CollectedContentBuilder content(String content) {
        this.content = content;
        return this;
    }

    public CollectedContentBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public CollectedContent build() {
        fillRequiredFields();
        return new CollectedContent(
                id,
                status,
                categoryList,
                summary,
                providerId,
                subscriptionId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = new CollectedContentId(LocalAutoIncrementIdUtils.nextId());
        }

        long fieldId = id.value();
        if (status == null) {
            status = CollectionStatus.COLLECTED;
        }
        if (categoryList == null) {
            categoryList = CollectionCategoryListBuilder.create();
        }
        if (summary == null) {
            summary = SummaryBuilder.create();
        }
        if (providerId == null) {
            providerId = new TechContentProviderId(LocalAutoIncrementIdUtils.nextId());
        }
        if (subscriptionId == null) {
            subscriptionId = new SubscriptionId(LocalAutoIncrementIdUtils.nextId());
        }
        if (url == null) {
            url = "url-" + fieldId;
        }
    }
}
