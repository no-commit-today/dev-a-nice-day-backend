package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectionCategory;

import javax.annotation.Nullable;
import java.util.List;

public record CategorizationResult(
        boolean success,
        @Nullable List<CollectionCategory> categories
) {

    public static CategorizationResult success(final List<CollectionCategory> categories) {
        return new CategorizationResult(true, categories);
    }

    public static CategorizationResult failure() {
        return new CategorizationResult(false, null);
    }
}
