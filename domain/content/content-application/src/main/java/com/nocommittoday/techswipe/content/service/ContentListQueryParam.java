package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;

import java.util.List;

public record ContentListQueryParam(
        List<TechCategory> categories
) {
    public ContentListQueryParam {
        if (categories == null) {
            categories = TechCategory.valueList();
        }
    }
}
