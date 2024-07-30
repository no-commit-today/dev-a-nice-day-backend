package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public record ContentCollectResponse(
        String id
) {

    public static ContentCollectResponse from(CollectedContentId id) {
        return new ContentCollectResponse(String.valueOf(id.value()));
    }
}
