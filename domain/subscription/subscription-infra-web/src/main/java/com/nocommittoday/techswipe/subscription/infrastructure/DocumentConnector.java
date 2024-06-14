package com.nocommittoday.techswipe.subscription.infrastructure;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
@RequiredArgsConstructor
public class DocumentConnector {

    public Document connect(final String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
        return document;
    }
}
