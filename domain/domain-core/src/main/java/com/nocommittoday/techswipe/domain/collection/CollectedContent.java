package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionCategorizeUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionInitializationFailureException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionSummarizeUnableException;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContent;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class CollectedContent {

    private static final int MIN_TOKEN_COUNT = 100;

    private final CollectedContentId id;

    private final CollectionStatus status;

    @Nullable
    private final CollectionCategoryList categoryList;

    @Nullable
    private final Summary summary;

    private final TechContentProviderId providerId;

    private final SubscriptionId subscriptionId;

    @Nullable
    private final TechContentId publishedContentId;

    private final String url;

    @Nullable
    private final String title;

    @Nullable
    private final LocalDate publishedDate;

    @Nullable
    private final String content;

    @Nullable
    private final String imageUrl;

    public CollectedContent(
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

    public static CollectedContent collect(
            CollectedContentId id,
            boolean initialized,
            TechContentProviderId providerId,
            SubscriptionId subscriptionId,
            String url,
            @Nullable String title,
            @Nullable LocalDate publishedDate,
            @Nullable String content,
            @Nullable String imageUrl
    ) {
        CollectedContent collectedContent = new CollectedContent(
                id,
                initialized ? CollectionStatus.INIT : CollectionStatus.COLLECTED,
                null,
                null,
                providerId,
                subscriptionId,
                null,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
        collectedContent.validateInitialized();
        return collectedContent;
    }

    public CollectedContent initialize(
            @Nullable String title,
            @Nullable LocalDate publishedDate,
            @Nullable String content,
            @Nullable String imageUrl
    ) {
        CollectedContent collectedContent = CollectedContentBuilder.from(this)
                .status(CollectionStatus.INIT)
                .title(title != null ? title : this.title)
                .publishedDate(publishedDate != null ? publishedDate : this.publishedDate)
                .content(content != null ? content : this.content)
                .imageUrl(imageUrl != null ? imageUrl : this.imageUrl)
                .build();

        collectedContent.validateInitialized();

        if (collectedContent.calculateContentTokenCount() < MIN_TOKEN_COUNT) {
            return collectedContent.filtered();
        }

        return collectedContent;
    }

    public CollectedContent initializationFailed() {
        return CollectedContentBuilder.from(this)
                .status(CollectionStatus.INIT_FAILED)
                .build();
    }

    public CollectedContent filtered() {
        return CollectedContentBuilder.from(this)
                .status(CollectionStatus.FILTERED)
                .build();
    }

    private void validateInitialized() {
        if (CollectionStatus.COLLECTED == status) {
            return;
        }

        if (title == null || publishedDate == null || content == null) {
            throw new CollectionInitializationFailureException(id);
        }
    }

    public int calculateContentTokenCount() {
        return (int) Arrays.stream(Objects.requireNonNull(content).split("[ \t\n]"))
                .filter(token -> !token.isBlank())
                .count();
    }

    public CollectedContent categorize(CollectionCategoryList categoryList) {
        if (!status.categorizable()) {
            throw new CollectionCategorizeUnableException(id, status);
        }
        CollectionStatus nextStatus = categoryList.containsUnused()
                ? CollectionStatus.FILTERED : CollectionStatus.CATEGORIZED;

        return CollectedContentBuilder.from(this)
                .status(nextStatus)
                .categoryList(categoryList)
                .build();
    }

    public CollectedContent failCategorization() {
        if (!status.categorizable()) {
            throw new CollectionCategorizeUnableException(id, status);
        }

        return CollectedContentBuilder.from(this)
                .status(CollectionStatus.CATEGORIZATION_FAILED)
                .build();
    }

    public CollectedContent summarize(Summary summary) {
        if (!status.summarizable()) {
            throw new CollectionSummarizeUnableException(id, status);
        }

        return CollectedContentBuilder.from(this)
                .status(CollectionStatus.SUMMARIZED)
                .summary(summary)
                .build();
    }

    public CollectedContent failSummarization() {
        if (!status.summarizable()) {
            throw new CollectionSummarizeUnableException(id, status);
        }

        return CollectedContentBuilder.from(this)
                .status(CollectionStatus.SUMMARIZATION_FAILED)
                .build();
    }

    public CollectedContent published(TechContentId publishedContentId) {
        if (!status.publishable()) {
            throw new CollectionPublishUnableException(id, status);
        }

        return CollectedContentBuilder.from(this)
                .status(CollectionStatus.PUBLISHED)
                .publishedContent(publishedContentId)
                .build();
    }

    public TechContent toTechContent(@Nullable ImageId imageId) {
        if (CollectionStatus.PUBLISHED != status) {
            throw new IllegalStateException("[" + CollectionStatus.PUBLISHED + "] 상태에서 호출해야 합니다. " +
                    "status=" + status + ", id=" + id);
        }

        return new TechContent(
                publishedContentId,
                providerId,
                imageId,
                url,
                title,
                publishedDate,
                summary,
                Objects.requireNonNull(categoryList).toTechCategories()
        );
    }

    public CollectedContentId getId() {
        return id;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    @Nullable
    public CollectionCategoryList getCategoryList() {
        return categoryList;
    }

    @Nullable
    public Summary getSummary() {
        return summary;
    }

    public TechContentProviderId getProviderId() {
        return providerId;
    }

    public SubscriptionId getSubscriptionId() {
        return subscriptionId;
    }

    @Nullable
    public TechContentId getPublishedContentId() {
        return publishedContentId;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
}
