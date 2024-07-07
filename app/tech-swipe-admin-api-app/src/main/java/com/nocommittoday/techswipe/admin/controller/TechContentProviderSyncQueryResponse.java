package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import jakarta.annotation.Nullable;

public record TechContentProviderSyncQueryResponse(
        long id,
        TechContentProviderType type,
        String title,
        String url,
        @Nullable Long iconId
) {

        public static TechContentProviderSyncQueryResponse from(TechContentProvider techContentProvider) {
            return new TechContentProviderSyncQueryResponse(
                    techContentProvider.getId().value(),
                    techContentProvider.getType(),
                    techContentProvider.getTitle(),
                    techContentProvider.getUrl(),
                    techContentProvider.getIconId() != null ? techContentProvider.getIconId().value() : null
            );
        }
}
