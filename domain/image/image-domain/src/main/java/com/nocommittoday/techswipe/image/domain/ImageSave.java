package com.nocommittoday.techswipe.image.domain;

import lombok.NonNull;

public record ImageSave(
        @NonNull String url,
        @NonNull String originalUrl,
        @NonNull String storedName
) {
}
