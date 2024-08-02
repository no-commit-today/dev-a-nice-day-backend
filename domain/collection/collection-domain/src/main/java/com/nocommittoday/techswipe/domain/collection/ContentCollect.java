package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record ContentCollect(
        CollectedContentId id,
        TechContentProviderId providerId,
        String url,
        String title,
        LocalDate publishedDate,
        String content,
        @Nullable String imageUrl
) {
}
