package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ContentCategoryEditRequest(
        @NotNull
        @Size(min = 1, message = "카테고리 개수는 1개 이상이어야 합니다.")
        List<CollectionCategory> categories
) {
}
