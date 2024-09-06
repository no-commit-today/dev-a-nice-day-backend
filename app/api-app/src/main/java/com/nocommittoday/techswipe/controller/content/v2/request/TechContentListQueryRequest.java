package com.nocommittoday.techswipe.controller.content.v2.request;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentListQueryParamNew;
import jakarta.validation.constraints.Positive;

import javax.annotation.Nullable;
import java.util.List;

public record TechContentListQueryRequest(
        @Nullable @Positive Long id,
        @Nullable List<TechCategory> categories,
        @Nullable @Positive Integer size
) {

    public TechContentListQueryParamNew toParam() {
        return new TechContentListQueryParamNew(
                id,
                categories,
                size
        );
    }
}
