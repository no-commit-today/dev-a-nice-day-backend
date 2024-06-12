package com.nocommittoday.techswipe.content.domain.vo;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.Image;
import lombok.NonNull;

import javax.annotation.Nullable;

public record ProviderSave(
        @NonNull TechContentProviderType type,
        @NonNull String title,
        @NonNull String url,
        @Nullable Image.ImageId iconId
) {
}
