package com.nocommittoday.techswipe.domain.content;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public record TechContentListQueryParamNew(
        @Nullable TechContentId id,
        List<TechCategory> categories,
        int size
) {

    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    public TechContentListQueryParamNew {
        if (size < 1) {
            throw new IllegalArgumentException("size 는 0 보다 커야 합니다.");
        }
        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }
    }

    public TechContentListQueryParamNew(
            @Nullable Long lastContentId,
            @Nullable List<TechCategory> categories,
            @Nullable Integer size
    ) {
        this(
                Optional.ofNullable(lastContentId)
                        .map(TechContentId::new)
                        .orElse(null),
                categories == null ? TechCategory.valueList() : categories,
                size == null ? DEFAULT_SIZE : size
        );
    }
}
