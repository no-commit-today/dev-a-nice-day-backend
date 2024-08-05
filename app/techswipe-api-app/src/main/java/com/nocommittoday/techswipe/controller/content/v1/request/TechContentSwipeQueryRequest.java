package com.nocommittoday.techswipe.controller.content.v1.request;

import com.nocommittoday.techswipe.domain.content.TechCategory;

import javax.annotation.Nullable;
import java.util.List;

public record TechContentSwipeQueryRequest(
        @Nullable List<TechCategory> categories
) {
}
