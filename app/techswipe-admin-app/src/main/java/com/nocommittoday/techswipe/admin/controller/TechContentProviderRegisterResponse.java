package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;

public record TechContentProviderRegisterResponse(
        String id
) {

    public static TechContentProviderRegisterResponse from(TechContentProviderId id) {
        return new TechContentProviderRegisterResponse(String.valueOf(id.value()));
    }
}
