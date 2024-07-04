package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import lombok.NonNull;

import javax.annotation.Nullable;

public record TechContentProviderRegisterCommand(
        @NonNull TechContentProviderType type,
        @NonNull String title,
        @NonNull String url,
        @Nullable Image.Id iconId
) {
}