package com.nocommittoday.techswipe.content.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.NonNull;

import javax.annotation.Nullable;

public record ProviderRegisterCommand(
        @NonNull TechContentProviderType type,
        @NonNull String title,
        @NonNull String url,
        @Nullable String iconUrl
) {
}
