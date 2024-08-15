package com.nocommittoday.techswipe.admin.domain;

import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.domain.image.ImageId;

import javax.annotation.Nullable;

public record AdminTechContentProviderRegisterCommand(
        TechContentProviderType type,
        String title,
        String url,
        @Nullable ImageId iconId
) {
}
