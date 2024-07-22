package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.Image;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentSync(
        TechContent.Id id,
        TechContentProvider.Id providerId,
        @Nullable Image.Id imageId,
        String url,
        String title,
        String summary,
        LocalDate publishedDate,
        List<TechCategory> categories,
        boolean deleted
) {
}
