package com.nocommittoday.techswipe.controller.content.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentQueryResult;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentQueryResponse(
        String id,
        String title,
        LocalDate publishedDate,
        String summary,
        @Nullable String imageUrl,
        List<TechCategory> categories,
        String providerId,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl,
        boolean bookmarked
) {

    public static TechContentQueryResponse from(TechContentQueryResult content) {
        return new TechContentQueryResponse(
                String.valueOf(content.content().getId().value()),
                content.content().getTitle(),
                content.content().getPublishedDate(),
                content.content().getSummary().getContent(),
                content.content().getImageUrl(),
                content.content().getCategories(),
                String.valueOf(content.content().getProvider().getId().value()),
                content.content().getProvider().getTitle(),
                content.content().getProvider().getUrl(),
                content.content().getProvider().getIconUrl(),
                content.bookmarked()
        );
    }
}
