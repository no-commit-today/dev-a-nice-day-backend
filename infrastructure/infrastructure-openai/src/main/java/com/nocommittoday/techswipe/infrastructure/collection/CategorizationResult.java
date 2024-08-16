package com.nocommittoday.techswipe.infrastructure.collection;

import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;

import javax.annotation.Nullable;
import java.util.Objects;

public record CategorizationResult(
        boolean success,
        @Nullable CollectionCategoryList categoryList,
        @Nullable Exception exception
) {

    public static CategorizationResult success(CollectionCategoryList categoryList) {
        return new CategorizationResult(true, categoryList, null);
    }

    public static CategorizationResult failure(Exception ex) {
        return new CategorizationResult(false, null, ex);
    }

    public CollectionCategoryList categoryList() {
        return Objects.requireNonNull(categoryList);
    }
}
