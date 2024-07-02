package com.nocommittoday.techswipe.image.infrastructure;

import lombok.NonNull;

public record ImageSave(
        @NonNull String url,
        @NonNull String originalUrl,
        @NonNull String storedName
) {
}
