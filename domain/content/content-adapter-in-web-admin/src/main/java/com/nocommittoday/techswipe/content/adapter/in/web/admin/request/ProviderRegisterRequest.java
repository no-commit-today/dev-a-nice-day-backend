package com.nocommittoday.techswipe.content.adapter.in.web.admin.request;

import com.nocommittoday.techswipe.content.application.port.in.ProviderRegisterCommand;
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