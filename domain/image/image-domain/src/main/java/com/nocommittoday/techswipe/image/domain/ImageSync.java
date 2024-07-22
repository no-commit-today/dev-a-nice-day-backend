package com.nocommittoday.techswipe.image.domain;

public record ImageSync(
        Image.Id id,
        String url,
        String originalUrl,
        String storedName,
        boolean deleted
) {
}
