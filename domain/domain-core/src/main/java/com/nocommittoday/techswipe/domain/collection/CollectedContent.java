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

    private final String title;

    private final LocalDate publishedDate;

    private final String content;

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
            String title,
            LocalDate publishedDate,
            String content,
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
        CollectedContent collectedContent = new CollectedContent(
                this.id,
                CollectionStatus.INIT,
                this.categoryList,
                this.summary,
                this.providerId,
                this.subscriptionId,
                this.publishedContentId,
                this.url,
                title != null ? title : this.title,
                publishedDate != null ? publishedDate : this.publishedDate,
                content != null ? content : this.content,
                imageUrl != null ? imageUrl : this.imageUrl
        );

        collectedContent.validateInitialized();

        if (collectedContent.calculateContentTokenCount() < MIN_TOKEN_COUNT) {
            return collectedContent.filtered();
        }

        return collectedContent;
    }

    public CollectedContent filtered() {
        return new CollectedContent(
                id,
                CollectionStatus.FILTERED,
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

    private void validateInitialized() {
        if (CollectionStatus.COLLECTED == status) {
            return;
        }

        if (title == null || publishedDate == null || content == null) {
            throw new CollectionInitializationFailureException(id);
        }
    }

    public int calculateContentTokenCount() {
        return (int) Arrays.stream(content.split("[ \t\n]"))
                .filter(token -> !token.isBlank())
                .count();
    }

    public CollectedContent categorize(CollectionCategoryList categoryList) {
        if (!status.categorizable()) {
            throw new CollectionCategorizeUnableException(id, status);
        }
        CollectionStatus nextStatus = categoryList.containsUnused()
                ? CollectionStatus.FILTERED : CollectionStatus.CATEGORIZED;

        return new CollectedContent(
                id,
                nextStatus,
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

    public CollectedContent failCategorization() {
        if (!status.categorizable()) {
            throw new CollectionCategorizeUnableException(id, status);
        }

        return new CollectedContent(
                id,
                CollectionStatus.CATEGORIZATION_FAILED,
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

    public CollectedContent summarize(Summary summary) {
        if (!status.summarizable()) {
            throw new CollectionSummarizeUnableException(id, status);
        }
        return new CollectedContent(
                id,
                CollectionStatus.SUMMARIZED,
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

    public CollectedContent failSummarization() {
        if (!status.summarizable()) {
            throw new CollectionSummarizeUnableException(id, status);
        }

        return new CollectedContent(
                id,
                CollectionStatus.SUMMARIZATION_FAILED,
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

    public CollectedContent published(TechContentId publishedContentId) {
        if (!status.publishable()) {
            throw new CollectionPublishUnableException(id, status);
        }
        return new CollectedContent(
                id,
                CollectionStatus.PUBLISHED,
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
