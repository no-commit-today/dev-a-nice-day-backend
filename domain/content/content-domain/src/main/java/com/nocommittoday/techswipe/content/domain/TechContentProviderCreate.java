package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.ImageId;

import javax.annotation.Nullable;

public record TechContentProviderCreate(
        TechContentProviderId id,
        TechContentProviderType type,
        String title,
        String url,
        @Nullable ImageId iconId
) {
}
