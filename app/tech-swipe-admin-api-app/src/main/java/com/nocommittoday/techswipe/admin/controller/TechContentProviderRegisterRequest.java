package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record TechContentProviderRegisterRequest(
        @NotNull TechContentProviderType type,
        @NotNull String title,
        @NotNull String url,
        @URL String iconUrl
) {
}
