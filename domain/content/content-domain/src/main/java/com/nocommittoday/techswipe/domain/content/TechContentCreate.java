package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.image.ImageId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentCreate(
        TechContentId id,
        TechContentProviderId providerId,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable ImageId imageId,
        String summary,
        List<TechCategory> categories
) {
    public TechContentCreate {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("카테고리는 최소 1개 이상이어야 합니다.");
        }
    }
}
