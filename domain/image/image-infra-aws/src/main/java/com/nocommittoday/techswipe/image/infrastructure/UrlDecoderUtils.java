package com.nocommittoday.techswipe.image.infrastructure;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public final class UrlDecoderUtils {

    public static String decode(final String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    private UrlDecoderUtils() {
        throw new UnsupportedOperationException();
    }
}
