package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.content.TechContentDetailQueryResult;

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
        @Nullable String providerIconUrl
) {

    public static TechContentDetailQueryResponse from(TechContentDetailQueryResult result) {
        return new TechContentDetailQueryResponse(
                String.valueOf(result.id().value()),
                result.url(),
                result.title(),
                result.publishedDate(),
                result.imageUrl(),
                result.summary(),
                result.categories(),
                String. valueOf(result.providerId().value()),
                result.providerType(),
                result.providerTitle(),
                result.providerUrl(),
                result.providerIconUrl()
        );
    }
}
