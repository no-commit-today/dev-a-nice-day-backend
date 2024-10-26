package com.nocommittoday.techswipe.controller.content.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentDetailQueryResult;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentDetailQueryResponse(
        String id,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories,
        String providerId,
        TechContentProviderType providerType,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl,
        boolean bookmarked
) {

    public static TechContentDetailQueryResponse from(TechContentDetailQueryResult result) {
        return new TechContentDetailQueryResponse(
                String.valueOf(result.content().getId().value()),
                result.content().getUrl(),
                result.content().getTitle(),
                result.content().getPublishedDate(),
                result.content().getImageUrl(),
                result.content().getSummary().getContent(),
                result.content().getCategories(),
                String.valueOf(result.content().getProvider().getId().value()),
                result.content().getProvider().getType(),
                result.content().getProvider().getTitle(),
                result.content().getProvider().getUrl(),
                result.content().getProvider().getIconUrl(),
                result.bookmarked()
        );
    }
}
