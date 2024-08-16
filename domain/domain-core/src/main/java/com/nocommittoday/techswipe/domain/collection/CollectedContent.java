package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.collection.exception.CollectionCategorizeUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.domain.collection.exception.CollectionSummarizeUnableException;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class CollectedContent {

    private final CollectedContentId id;

    private final CollectionStatus status;

    @Nullable
    private final CollectionCategoryList categoryList;

    @Nullable
    private final Summary summary;

    private final TechContentProviderId providerId;

    private final String url;

    private final String title;

    private final LocalDate publishedDate;

    private final String content;

    private final String imageUrl;

    public CollectedContent(
            CollectedContentId id,
            TechContentProviderId providerId,
            String url,
            String title,
            LocalDate publishedDate,
            String content,
            @Nullable String imageUrl
    ) {
        this.id = id;
        this.status = CollectionStatus.INIT;
        this.categoryList = null;
        this.summary = null;
        this.providerId = providerId;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // FIXME 해당 생성자를 사용하지 않는 방법 고민해야 겠음. status 가 도메인 로직에 의해서 변경되어야 함
    public CollectedContent(
            CollectedContentId id,
            CollectionStatus status,
            @Nullable CollectionCategoryList categoryList,
            @Nullable Summary summary,
            TechContentProviderId providerId,
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
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public static CollectedContent collect(
            ContentCollect contentCollect
    ) {
        return new CollectedContent(
                contentCollect.id(),
                CollectionStatus.INIT,
                null,
                null,
                contentCollect.providerId(),
                contentCollect.url(),
                contentCollect.title(),
                contentCollect.publishedDate(),
                contentCollect.content(),
                contentCollect.imageUrl()
        );
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
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    public CollectedContent published() {
        if (!status.publishable()) {
            throw new CollectionPublishUnableException(id, status);
        }
        return new CollectedContent(
                id,
                CollectionStatus.PUBLISHED,
                categoryList,
                summary,
                providerId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    @Nullable
    public CollectionCategoryList getCategoryList() {
        return categoryList;
    }

    public String getContent() {
        return content;
    }

    public CollectedContentId getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public TechContentProviderId getProviderId() {
        return providerId;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    @Nullable
    public Summary getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
