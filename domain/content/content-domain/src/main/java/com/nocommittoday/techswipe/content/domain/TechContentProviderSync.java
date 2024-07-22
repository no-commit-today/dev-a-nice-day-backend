package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;

import javax.annotation.Nullable;

public record TechContentProviderSync(
        TechContentProvider.Id id,
        TechContentProviderType type,
        String title,
        String url,
        @Nullable Image.Id iconId,
        boolean deleted
) {
}
