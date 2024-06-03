package com.nocommittoday.techswipe.collection.domain.vo;

import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.NonNull;

import javax.annotation.Nullable;

public record ContentCollect(
        @NonNull CollectionType type,
        @NonNull TechContentProvider.TechContentProviderId providerId,
        @NonNull String url,
        @NonNull String title,
        @NonNull String content,
        @Nullable String imageUrl
) {
}
