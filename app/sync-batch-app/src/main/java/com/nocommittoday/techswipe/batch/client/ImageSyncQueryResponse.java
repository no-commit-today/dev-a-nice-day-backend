package com.nocommittoday.techswipe.batch.client;

public record ImageSyncQueryResponse(
        long id,
        String url,
        String originalUrl,
        String storedName
) {
}
