package com.nocommittoday.techswipe.content.service;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.ImageId;

import javax.annotation.Nullable;

public record TechContentProviderRegisterCommand(
        TechContentProviderType type,
        String title,
        String url,
        @Nullable ImageId iconId
) {
}
