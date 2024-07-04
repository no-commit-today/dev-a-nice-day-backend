package com.nocommittoday.techswipe.content.domain;

import lombok.NonNull;

import java.util.List;

public record TechContentCategoryEdit(
        @NonNull List<TechCategory> categories
) {

    public TechContentCategoryEdit {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("카테고리 개수는 1개 이상이어야 합니다.");
        }
    }
}
