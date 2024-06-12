package com.nocommittoday.techswipe.content.controller.v1.response;

import com.nocommittoday.techswipe.content.domain.TechCategory;

import javax.annotation.Nullable;
import java.util.List;

public record ContentResponse(
        long id,
        String url,
        String title,
        String summary,
        @Nullable String imageUrl,
        List<TechCategory> categories,
        long providerId,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl
) {
}
