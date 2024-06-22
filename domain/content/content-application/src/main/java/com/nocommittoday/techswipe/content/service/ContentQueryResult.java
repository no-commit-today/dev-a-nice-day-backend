package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;

import javax.annotation.Nullable;
import java.util.List;

public record ContentQueryResult(
        TechContent.Id id,
        ProviderQueryResult provider,
        String url,
        String title,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories
) {
}
