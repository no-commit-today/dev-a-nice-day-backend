package com.nocommittoday.techswipe.batch.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class JsoupUtils {

    private JsoupUtils() {
        throw new UnsupportedOperationException();
    }

    public static Document getDocument(final String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
