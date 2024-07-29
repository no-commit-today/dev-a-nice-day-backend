package com.nocommittoday.techswipe.image.domain;

public record ImageSync(
        ImageId id,
        String url,
        String originalUrl,
        String storedName,
        boolean deleted
) {
}
