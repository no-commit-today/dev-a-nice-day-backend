package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionCategorizeUnableException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionPublishUnableException;
import com.nocommittoday.techswipe.collection.domain.exception.CollectionSummarizeUnableException;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Getter
public class CollectedContent {

    private final CollectedContentId id;

    private final CollectionType type;

    private final CollectionStatus status;

    @Nullable
    private final List<CollectionCategory> categories;

    @Nullable
    private final String summary;

    private final TechContentProvider.TechContentProviderId providerId;

    private final String url;

    private final String title;

    private final LocalDate publishedDate;

    private final String content;

    private final String imageUrl;

    public record CollectedContentId(long id) { }

    public CollectedContent(
            final CollectedContentId id,
            final CollectionType type,
            final TechContentProvider.TechContentProviderId providerId,
            final String url,
            final String title,
            final LocalDate publishedDate,
            final String content,
            @Nullable final String imageUrl
    ) {
        this.id = id;
        this.type = type;
        this.status = CollectionStatus.NONE;
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
            final CollectedContentId id,
            final CollectionType type,
            final CollectionStatus status,
            @Nullable final List<CollectionCategory> categories,
            @Nullable final String summary,
            final TechContentProvider.TechContentProviderId providerId,
            final String url,
            final String title,
            final LocalDate publishedDate,
            final String content,
            @Nullable final String imageUrl
    ) {
        this.id = id;
        this.type = type;
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

    public CollectedContent categorize(final List<CollectionCategory> categories) {
        if (status != CollectionStatus.NONE) {
            throw new CollectionCategorizeUnableException(id, status);
        }
        final CollectionStatus nextStatus = categories.stream()
                .anyMatch(category -> !category.isUsed())
                ? CollectionStatus.FILTERED : CollectionStatus.CATEGORIZED;

        return new CollectedContent(
                id,
                type,
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

    public CollectedContent summarize(final String summary) {
        if (status != CollectionStatus.CATEGORIZED) {
            throw new CollectionSummarizeUnableException(id, status);
        }
        return new CollectedContent(
                id,
                type,
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

    public CollectedContent published() {
        if (status != CollectionStatus.SUMMARIZED) {
            throw new CollectionPublishUnableException(id, status);
        }
        return new CollectedContent(
                id,
                type,
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
}
