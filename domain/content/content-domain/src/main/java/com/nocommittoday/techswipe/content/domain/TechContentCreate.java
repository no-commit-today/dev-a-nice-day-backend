package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentCreate(
        @NonNull TechContentProvider.Id providerId,
        @NonNull String url,
        @NonNull String title,
        @NonNull LocalDate publishedDate,
        @Nullable Image.Id imageId,
        @NonNull String summary,
        @NonNull List<TechCategory> categories
) {
    public TechContentCreate {
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("카테고리는 최소 1개 이상이어야 합니다.");
        }
    }
}
