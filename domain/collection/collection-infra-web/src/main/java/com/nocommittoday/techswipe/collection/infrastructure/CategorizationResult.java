package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectionCategory;

import javax.annotation.Nullable;
import java.util.List;

public record CategorizationResult(
        boolean success,
        @Nullable List<CollectionCategory> categories,
        @Nullable Exception exception
) {

    public static CategorizationResult success(List<CollectionCategory> categories) {
        return new CategorizationResult(true, categories, null);
    }

    public static CategorizationResult failure(Exception ex) {
        return new CategorizationResult(false, null, ex);
    }
}
