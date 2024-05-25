package com.nocommittoday.techswipe.content.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechCategory;

import java.util.List;

public record ContentListQueryParam(
        List<TechCategory> categories
) {
}
