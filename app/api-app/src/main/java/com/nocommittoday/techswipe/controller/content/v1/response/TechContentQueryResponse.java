package com.nocommittoday.techswipe.controller.content.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentQuery;

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
        @Nullable String providerIconUrl
) {

    public static TechContentQueryResponse from(TechContentQuery content) {
        return new TechContentQueryResponse(
                String.valueOf(content.getId().value()),
                content.getTitle(),
                content.getPublishedDate(),
                content.getSummary().getContent(),
                content.getImageUrl(),
                content.getCategories(),
                String.valueOf(content.getProvider().getId().value()),
                content.getProvider().getTitle(),
                content.getProvider().getUrl(),
                content.getProvider().getIconUrl()
        );
    }
}
