package com.nocommittoday.techswipe.infrastructure.jsoup;

import org.springframework.stereotype.Component;

@Component
public class HtmlDocumentCreator {

    public HtmlDocument create(String html, String url) {
        return HtmlDocument.create(html, url);
    }
}
