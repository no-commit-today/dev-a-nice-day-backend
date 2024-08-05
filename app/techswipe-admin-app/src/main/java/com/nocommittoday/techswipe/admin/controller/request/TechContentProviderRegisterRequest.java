package com.nocommittoday.techswipe.admin.controller.request;

import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record TechContentProviderRegisterRequest(
        @NotNull TechContentProviderType type,
        @NotNull String title,
        @NotNull String url,
        @URL @Nullable String iconUrl
) {
}
