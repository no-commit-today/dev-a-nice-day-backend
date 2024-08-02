package com.nocommittoday.techswipe.domain.collection;

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
