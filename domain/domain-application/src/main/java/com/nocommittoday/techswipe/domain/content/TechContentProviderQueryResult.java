package com.nocommittoday.techswipe.domain.content;

import javax.annotation.Nullable;

public record TechContentProviderQueryResult(
        TechContentProviderId id,
        String title,
        String url,
        @Nullable String iconUrl
) {
}
