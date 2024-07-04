package com.nocommittoday.techswipe.collection.domain;

import lombok.NonNull;

import java.util.List;
import java.util.Set;

public record ContentCategoryEdit(
        @NonNull List<CollectionCategory> categories
) {

    public ContentCategoryEdit {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("카테고리 개수는 1개 이상이어야 합니다.");
        }
    }

    private static final Set<CollectionStatus> EDITABLE_STATUSES = Set.of(
            CollectionStatus.FILTERED, CollectionStatus.PUBLISHED);

    public boolean isEditable(final CollectionStatus status) {
        return EDITABLE_STATUSES.contains(status);
    }

    public CollectionStatus nextContentStatus(final CollectedContent collectedContent) {
        final CollectionStatus prevStatus = collectedContent.getStatus();
        if (!isEditable(prevStatus)) {
            throw new CollectionCategoryNotEditableException(collectedContent.getId());
        }
        final boolean containsUnused = categories.stream()
                .anyMatch(category -> !category.isUsed());

        if (containsUnused) {
            return CollectionStatus.FILTERED;
        }

        if (CollectionStatus.PUBLISHED == prevStatus) {
            return CollectionStatus.PUBLISHED;
        } else if (CollectionStatus.FILTERED == prevStatus) {
            return CollectionStatus.CATEGORIZED;
        }

        throw new IllegalArgumentException("카테고리를 수정할 수 없는 상태입니다. status=" + prevStatus);
    }
}
