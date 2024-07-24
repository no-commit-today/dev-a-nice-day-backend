package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;

import javax.annotation.Nullable;

public record TechContentProviderQueryResult(
        TechContentProviderId id,
        String title,
        String url,
        @Nullable String iconUrl
) {
}
