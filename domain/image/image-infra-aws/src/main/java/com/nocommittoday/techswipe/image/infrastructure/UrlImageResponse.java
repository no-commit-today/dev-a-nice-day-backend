package com.nocommittoday.techswipe.image.infrastructure;

import javax.annotation.Nullable;

public record UrlImageResponse(
        boolean success,
        @Nullable ImageData data,
        @Nullable Exception exception
) {

    public static UrlImageResponse success(final ImageData data) {
        return new UrlImageResponse(true, data, null);
    }

    public static UrlImageResponse failure(final Exception exception) {
        return new UrlImageResponse(false, null, exception);
    }
}
