package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Getter
public class CollectedContent {

    @NonNull
    private final CollectedContentId id;

    @NonNull
    private final CollectionType type;

    @NonNull
    private final CollectionStatus status;

    @Nullable
    private final List<CollectionCategory> categories;

    @Nullable
    private final String summary;

    @NonNull
    private final TechContentProvider.TechContentProviderId providerId;

    @NonNull
    private final String url;

    @NonNull
    private final String title;

    @NonNull
    private final LocalDate publishedDate;

    @NonNull
    private final String content;

    @Nullable
    private final String imageUrl;

    public record CollectedContentId(long id) { }

    public CollectedContent(
            @NonNull final CollectedContentId id,
            @NonNull final CollectionType type,
            @NonNull final TechContentProvider.TechContentProviderId providerId,
            @NonNull final String url,
            @NonNull final String title,
            @NonNull final LocalDate publishedDate,
            @NonNull final String content,
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

    private CollectedContent(
            @NonNull final CollectedContentId id,
            @NonNull final CollectionType type,
            @NonNull final CollectionStatus status,
            @Nullable final List<CollectionCategory> categories,
            @Nullable final String summary,
            @NonNull final TechContentProvider.TechContentProviderId providerId,
            @NonNull final String url,
            @NonNull final String title,
            @NonNull final LocalDate publishedDate,
            @NonNull final String content,
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
            throw new IllegalStateException("카테고리가 분류되지 않은 컨텐츠는 요약할 수 없습니다.");
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
}
