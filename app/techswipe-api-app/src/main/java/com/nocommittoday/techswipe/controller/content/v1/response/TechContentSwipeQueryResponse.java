package com.nocommittoday.techswipe.controller.content.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentSwipeQueryResult;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record TechContentSwipeQueryResponse(
        String id,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories,
        String providerId,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl
) {
    public static TechContentSwipeQueryResponse from(TechContentSwipeQueryResult swipe) {
        return new TechContentSwipeQueryResponse(
                String.valueOf(swipe.id().value()),
                swipe.url(),
                swipe.title(),
                swipe.publishedDate(),
                swipe.imageUrl(),
                swipe.summary(),
                swipe.categories(),
                String.valueOf(swipe.providerId().value()),
                swipe.providerTitle(),
                swipe.providerUrl(),
                swipe.providerIconUrl()
        );
    }
}
