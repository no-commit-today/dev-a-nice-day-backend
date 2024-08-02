package com.nocommittoday.techswipe.domain.content;

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
