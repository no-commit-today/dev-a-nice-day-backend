package com.nocommittoday.techswipe.content.adapter.in.web.v1.request;

import com.nocommittoday.techswipe.content.domain.TechCategory;

import java.util.List;

public record ContentListQueryRequest(
        List<TechCategory> categories
) {
}
