package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import lombok.NonNull;

public record PromptRegister(
        @NonNull PromptType type,
        @NonNull TechContentProviderType providerType,
        @NonNull String version,
        @NonNull String model,
        @NonNull String content
) {
}
