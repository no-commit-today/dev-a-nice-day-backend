package com.nocommittoday.techswipe.admin.controller.response;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;

public record ContentCollectResponse(
        String id
) {

    public static ContentCollectResponse from(CollectedContentId id) {
        return new ContentCollectResponse(String.valueOf(id.value()));
    }
}
