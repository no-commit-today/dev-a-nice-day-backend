package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.ImageId;
import lombok.NonNull;

import javax.annotation.Nullable;

public record TechContentProviderCreate(
        @NonNull TechContentProviderId id,
        @NonNull TechContentProviderType type,
        @NonNull String title,
        @NonNull String url,
        @Nullable ImageId iconId
) {
}
