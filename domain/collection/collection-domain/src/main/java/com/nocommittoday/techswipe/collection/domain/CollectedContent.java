package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.exception.CollectionCategorizeUnableException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionCategoryNotEditableException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionSummarizeUnableException;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Getter
public class CollectedContent {

    private final CollectedContentId id;

    private final CollectionStatus status;

    @Nullable
    private final List<CollectionCategory> categories;

    @Nullable
    private final String summary;

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
        this.categories = null;
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
            @Nullable List<CollectionCategory> categories,
            @Nullable String summary,
            TechContentProviderId providerId,
            String url,
            String title,
            LocalDate publishedDate,
            String content,
            @Nullable String imageUrl
    ) {
        this.id = id;
        this.status = status;
        this.categories = categories;
        this.summary = summary;
        this.providerId = providerId;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public CollectedContent categorize(List<CollectionCategory> categories) {
        if (!status.categorizable()) {
            throw new CollectionCategorizeUnableException(id, status);
        }
        CollectionStatus nextStatus = categories.stream()
                .anyMatch(category -> !category.isUsed())
                ? CollectionStatus.FILTERED : CollectionStatus.CATEGORIZED;

        return new CollectedContent(
                id,
                nextStatus,
                categories,
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
                categories,
                summary,
                providerId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    public CollectedContent summarize(String summary) {
        if (!status.summarizable()) {
            throw new CollectionSummarizeUnableException(id, status);
        }
        return new CollectedContent(
                id,
                CollectionStatus.SUMMARIZED,
                categories,
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
                categories,
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
                categories,
                summary,
                providerId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    public CollectedContent editCategory(ContentCategoryEdit categoryEdit) {
        if (!categoryEdit.isEditable(status)) {
            throw new CollectionCategoryNotEditableException(id);
        }

        CollectionStatus nextStatus = categoryEdit.nextContentStatus(this);

        return new CollectedContent(
                id,
                nextStatus,
                categoryEdit.categories(),
                summary,
                providerId,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }
}
