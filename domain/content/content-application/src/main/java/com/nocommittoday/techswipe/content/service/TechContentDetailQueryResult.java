package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record TechContentDetailQueryResult(
        TechContentId id,
        String url,
        String title,
        LocalDate publishedDate,
        @Nullable String imageUrl,
        String summary,
        List<TechCategory> categories,
        TechContentProviderId providerId,
        TechContentProviderType providerType,
        String providerTitle,
        String providerUrl,
        @Nullable String providerIconUrl
) {
}
