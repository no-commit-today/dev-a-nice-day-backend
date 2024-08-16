package com.nocommittoday.techswipe.admin.controller.response;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

public record AdminTechContentProviderRegisterResponse(
        String id
) {

    public static AdminTechContentProviderRegisterResponse from(TechContentProviderId id) {
        return new AdminTechContentProviderRegisterResponse(String.valueOf(id.value()));
    }
}
