package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.image.domain.Image;

public record ImageSyncQueryResponse(
        long id,
        String url,
        String originalUrl,
        String storedName
) {

    public static ImageSyncQueryResponse from(final Image image) {
        return new ImageSyncQueryResponse(
                image.getId().value(),
                image.getUrl(),
                image.getOriginalUrl(),
                image.getStoredName()
        );
    }
}
