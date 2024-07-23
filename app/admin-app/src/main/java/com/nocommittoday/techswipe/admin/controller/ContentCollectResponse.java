package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;

public record ContentCollectResponse(
        Long id
) {

    public static ContentCollectResponse from(CollectedContent.Id id) {
        return new ContentCollectResponse(id.value());
    }
}
