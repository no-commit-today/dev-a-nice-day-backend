package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.PromptRegister;
import com.nocommittoday.techswipe.collection.domain.PromptType;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import jakarta.validation.constraints.NotNull;

public record PromptRegisterRequest(
        @NotNull PromptType type,
        @NotNull TechContentProviderType providerType,
        @NotNull String version,
        @NotNull String model,
        @NotNull String content
) {

    public PromptRegister toCommand() {
        return new PromptRegister(type, providerType, version, model, content);
    }
}
