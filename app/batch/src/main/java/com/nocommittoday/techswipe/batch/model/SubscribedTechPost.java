package com.nocommittoday.techswipe.batch.model;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SubscribedTechPost(
        String url,
        String title,
        @Nullable String imageUrl,
        LocalDate publishedDate,
        String content
) {
}