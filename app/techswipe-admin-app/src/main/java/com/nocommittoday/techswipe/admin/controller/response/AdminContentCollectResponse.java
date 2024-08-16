package com.nocommittoday.techswipe.admin.controller.response;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;

public record AdminContentCollectResponse(
        String id
) {

    public static AdminContentCollectResponse from(CollectedContentId id) {
        return new AdminContentCollectResponse(String.valueOf(id.value()));
    }
}
