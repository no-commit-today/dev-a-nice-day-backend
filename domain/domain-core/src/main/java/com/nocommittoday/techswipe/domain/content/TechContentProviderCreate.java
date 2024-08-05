package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.image.ImageId;

import javax.annotation.Nullable;

public record TechContentProviderCreate(
        TechContentProviderId id,
        TechContentProviderType type,
        String title,
        String url,
        @Nullable ImageId iconId
) {
}
