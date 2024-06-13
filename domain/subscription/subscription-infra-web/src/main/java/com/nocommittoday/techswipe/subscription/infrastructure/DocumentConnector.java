package com.nocommittoday.techswipe.subscription.infrastructure;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UncheckedIOException;

@RequiredArgsConstructor
class DocumentConnector {

    private final String url;

    Document connect() {
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
        return document;
    }
}
