package com.nocommittoday.techswipe.batch.client;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import jakarta.annotation.Nullable;

public record TechContentProviderSyncQueryResponse(
        long id,
        TechContentProviderType type,
        String title,
        String url,
        @Nullable Long iconId
) {
}
