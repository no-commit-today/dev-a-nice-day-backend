package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.image.domain.Image;

import javax.annotation.Nullable;
import java.util.List;

public record ContentResult(
        TechContent.TechContentId id,
        ProviderResult provider,
        String url,
        String title,
        @Nullable Image.ImageId imageId,
        String summary,
        List<TechCategory> categories
) {

    public static ContentResult from(final TechContent content) {
        return new ContentResult(
                content.getId(),
                ProviderResult.from(content.getProvider()),
                content.getUrl(),
                content.getTitle(),
                content.getImageId(),
                content.getSummary(),
                content.getCategories()
        );
    }
}
