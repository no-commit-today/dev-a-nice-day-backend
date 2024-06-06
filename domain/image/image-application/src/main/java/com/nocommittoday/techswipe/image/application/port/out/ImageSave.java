package com.nocommittoday.techswipe.image.application.port.out;

import lombok.NonNull;

public record ImageSave(
        @NonNull String url,
        @NonNull String originalUrl,
        @NonNull String storedName
) {
}
