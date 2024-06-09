package com.nocommittoday.techswipe.content.domain.vo;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.image.domain.Image;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentCreate(
        TechContentProvider.TechContentProviderId providerId,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable Image.ImageId imageId,
        String summary,
        List<TechCategory> categories
) {
    public TechContentCreate {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("카테고리는 최소 1개 이상이어야 합니다.");
        }
    }
}
