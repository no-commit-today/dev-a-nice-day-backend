package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

import javax.annotation.Nullable;

public record ProviderResult(
        TechContentProvider.TechContentProviderId id,
        String title,
        String url,
        @Nullable String iconUrl
) {
}
