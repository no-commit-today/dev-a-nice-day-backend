package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.content.service.ProviderRegisterCommand;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record ProviderRegisterRequest(
        @NotNull TechContentProviderType type,
        @NotNull String title,
        @NotNull String url,
        @URL String iconUrl
) {

    public ProviderRegisterCommand toCommand() {
        return new ProviderRegisterCommand(type, title, url, iconUrl);
    }
}
