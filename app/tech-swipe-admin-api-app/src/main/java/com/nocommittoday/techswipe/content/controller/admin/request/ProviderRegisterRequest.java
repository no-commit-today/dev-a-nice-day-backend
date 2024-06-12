package com.nocommittoday.techswipe.content.controller.admin.request;

import com.nocommittoday.techswipe.content.service.ProviderRegisterCommand;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import jakarta.validation.constraints.NotNull;

public record ProviderRegisterRequest(
        @NotNull TechContentProviderType type,
        @NotNull String title,
        @NotNull String url,
        String iconUrl
) {

    public ProviderRegisterCommand toCommand() {
        return new ProviderRegisterCommand(type, title, url, iconUrl);
    }
}
