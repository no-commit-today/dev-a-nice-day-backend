package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record ContentCollect(
        @NonNull CollectedContentId id,
        @NonNull TechContentProviderId providerId,
        @NonNull String url,
        @NonNull String title,
        @NonNull LocalDate publishedDate,
        @NonNull String content,
        @Nullable String imageUrl
) {
}
