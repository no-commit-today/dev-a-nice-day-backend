package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.provider.TechContentProviderQueryResult;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentQueryResult(
        TechContentId id,
        TechContentProviderQueryResult provider,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories
) {
}
