package com.nocommittoday.techswipe.batch.client;

import com.nocommittoday.techswipe.content.domain.TechCategory;
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
}
