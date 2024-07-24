package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.image.domain.ImageId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentSync(
        TechContentId id,
        TechContentProviderId providerId,
        @Nullable ImageId imageId,
        String url,
        String title,
        String summary,
        LocalDate publishedDate,
        List<TechCategory> categories,
        boolean deleted
) {
}
