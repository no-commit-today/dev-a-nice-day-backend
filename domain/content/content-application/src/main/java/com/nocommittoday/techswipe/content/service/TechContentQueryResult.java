package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentQueryResult(
        TechContent.Id id,
        TechContentProviderQueryResult provider,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories
) {
}
