package com.nocommittoday.techswipe.content.controller.v1;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.service.ContentQueryResult;

import javax.annotation.Nullable;
import java.util.List;

public record ContentResponse(
        long id,
        String url,
        String title,
        String summary,
        @Nullable String imageUrl,
        List<TechCategory> categories,
        long providerId,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl
) {

    public static ContentResponse from(final ContentQueryResult content, final String link) {
        return new ContentResponse(
                content.id().value(),
                link,
                content.title(),
                content.summary(),
                content.imageUrl(),
                content.categories(),
                content.provider().id().value(),
                content.provider().title(),
                content.provider().url(),
                content.provider().iconUrl()
        );
    }
}
