package com.nocommittoday.techswipe.domain.content;

import java.util.List;

public record TechContentSwipeQueryParam(
        List<TechCategory> categories
) {

    public TechContentSwipeQueryParam {
        if (categories == null) {
            categories = TechCategory.valueList();
        }
    }
}
