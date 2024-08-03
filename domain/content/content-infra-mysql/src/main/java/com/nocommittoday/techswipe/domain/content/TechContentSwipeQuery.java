package com.nocommittoday.techswipe.domain.content;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record TechContentSwipeQuery(
        TechContentId id,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories,
        TechContentProviderId providerId,
        TechContentProviderType providerType,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl
) {
}
