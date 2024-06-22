package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;
import lombok.NonNull;

import javax.annotation.Nullable;

public record TechContentProviderCreate(
        @NonNull TechContentProviderType type,
        @NonNull String title,
        @NonNull String url,
        @Nullable Image.Id iconId
) {
}
