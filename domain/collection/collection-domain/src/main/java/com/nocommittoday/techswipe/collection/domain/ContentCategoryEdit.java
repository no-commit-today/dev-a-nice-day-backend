package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.collection.domain.exception.CollectionCategoryNotEditableException;
import com.nocommittoday.techswipe.content.domain.TechContentCategoryEdit;
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

    public boolean isEditable(CollectionStatus status) {
        return EDITABLE_STATUSES.contains(status);
    }

    public CollectionStatus nextContentStatus(CollectedContent collectedContent) {
        CollectionStatus prevStatus = collectedContent.getStatus();
        if (!isEditable(prevStatus)) {
            throw new CollectionCategoryNotEditableException(collectedContent.getId());
        }

        if (containsUnusedCategory()) {
            return CollectionStatus.FILTERED;
        }

        if (CollectionStatus.PUBLISHED == prevStatus) {
            return CollectionStatus.PUBLISHED;
        } else if (CollectionStatus.FILTERED == prevStatus) {
            return CollectionStatus.CATEGORIZED;
        }

        throw new CollectionCategoryNotEditableException(collectedContent.getId());
    }

    public boolean containsUnusedCategory() {
        return categories.stream()
                .anyMatch(category -> !category.isUsed());
    }

    public TechContentCategoryEdit toTechContentCategoryEdit() {
        return new TechContentCategoryEdit(categories
                .stream()
                .map(CollectionCategory::getTechCategory)
                .toList()
        );
    }
}
