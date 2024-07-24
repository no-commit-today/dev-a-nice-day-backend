package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public record ContentCollectResponse(
        Long id
) {

    public static ContentCollectResponse from(CollectedContentId id) {
        return new ContentCollectResponse(id.value());
    }
}
