package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.domain.content.TechCategory;

import javax.annotation.Nullable;
import java.util.List;

public record TechContentListQueryRequest(
        @Nullable List<TechCategory> categories
) {
}
