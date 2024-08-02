package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.image.domain.ImageId;

import javax.annotation.Nullable;

public record TechContentProviderRegisterCommand(
        TechContentProviderType type,
        String title,
        String url,
        @Nullable ImageId iconId
) {
}
