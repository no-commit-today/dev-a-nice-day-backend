package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;

import java.util.List;

public record TechContentListQueryParam(
        List<TechCategory> categories
) {
    public TechContentListQueryParam {
        if (categories == null) {
            categories = TechCategory.valueList();
        }
    }
}
