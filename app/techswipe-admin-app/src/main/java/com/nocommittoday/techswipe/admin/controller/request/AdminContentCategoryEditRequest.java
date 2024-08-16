package com.nocommittoday.techswipe.admin.controller.request;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AdminContentCategoryEditRequest(
        @NotNull
        @Size(
                min = CollectionCategoryList.MIN_SIZE,
                max = CollectionCategoryList.MAX_SIZE
        )
        List<CollectionCategory> categories
) {

        public CollectionCategoryList toCollectionCategoryList() {
                return CollectionCategoryList.create(categories);
        }
}
