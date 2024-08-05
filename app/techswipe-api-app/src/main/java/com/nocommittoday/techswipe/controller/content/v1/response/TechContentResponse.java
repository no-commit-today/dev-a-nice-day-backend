package com.nocommittoday.techswipe.controller.content.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentQueryResult;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentResponse(
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

    public static TechContentResponse from(TechContentQueryResult content) {
        return new TechContentResponse(
                String.valueOf(content.id().value()),
                content.title(),
                content.publishedDate(),
                content.summary(),
                content.imageUrl(),
                content.categories(),
                String.valueOf(content.provider().id().value()),
                content.provider().title(),
                content.provider().url(),
                content.provider().iconUrl()
        );
    }
}
