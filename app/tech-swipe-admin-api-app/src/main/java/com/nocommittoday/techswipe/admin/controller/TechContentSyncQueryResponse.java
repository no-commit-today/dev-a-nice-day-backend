package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public record TechContentSyncQueryResponse(
        long id,
        long providerId,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable Long imageId,
        String summary,
        List<TechCategory> categories
) {

    public static TechContentSyncQueryResponse from(TechContent techContent) {
        return new TechContentSyncQueryResponse(
                techContent.getId().value(),
                techContent.getProvider().getId().value(),
                techContent.getUrl(),
                techContent.getTitle(),
                techContent.getPublishedDate(),
                techContent.getImageId() != null ? techContent.getImageId().value() : null,
                techContent.getSummary(),
                techContent.getCategories()
        );
    }
}
