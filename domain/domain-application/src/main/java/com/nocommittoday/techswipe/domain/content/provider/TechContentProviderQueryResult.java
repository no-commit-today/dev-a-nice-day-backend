package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

import javax.annotation.Nullable;

public record TechContentProviderQueryResult(
        TechContentProviderId id,
        String title,
        String url,
        @Nullable String iconUrl
) {
}
