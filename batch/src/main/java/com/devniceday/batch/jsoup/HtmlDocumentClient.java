package com.devniceday.batch.jsoup;

import jakarta.annotation.Nullable;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class HtmlDocumentClient {

    private final HtmlClient htmlClient;

    public HtmlDocumentClient(HtmlClient htmlClient) {
        this.htmlClient = htmlClient;
    }

    @Nullable
    public HtmlDocument get(String url) {
        String html = htmlClient.get(url);
        if (html == null) {
            return null;
        }

        return new HtmlDocument(Jsoup.parse(html, url));
    }
}
