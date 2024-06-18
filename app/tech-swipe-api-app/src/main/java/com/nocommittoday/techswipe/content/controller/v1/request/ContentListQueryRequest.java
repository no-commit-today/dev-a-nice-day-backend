package com.nocommittoday.techswipe.content.controller.v1.request;

import com.nocommittoday.techswipe.content.domain.TechCategory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public record ContentListQueryRequest(
        @Nullable List<TechCategory> categories
) {
    public ContentListQueryRequest {
        if (categories == null) {
            categories = Arrays.stream(TechCategory.values()).toList();
        }
    }
}
