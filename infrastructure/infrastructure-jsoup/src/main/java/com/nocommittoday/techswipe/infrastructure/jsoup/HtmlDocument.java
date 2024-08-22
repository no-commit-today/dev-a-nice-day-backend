package com.nocommittoday.techswipe.infrastructure.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class HtmlDocument {

    private final Document document;

    HtmlDocument(Document document) {
        this.document = document;
    }

    static HtmlDocument create(String html, String url) {
        return new HtmlDocument(Jsoup.parse(html, url));
    }

    @Nullable
    public String openGraphImage() {
        return Optional.ofNullable(
                        document
                                .head()
                                .select("meta[property=og:image]")
                                .first()
                )
                .map(element -> element.attr("content"))
                .filter(url -> !url.isBlank())
                .orElse(null);
    }

    public String title() {
        return document.title();
    }

    public String textByIndex(List<Integer> indexes) {
        Element element = elementByIndex(indexes);
        if (!element.children().isEmpty()) {
            throw new HtmlDocumentScrapException("uri=" + document.baseUri() + ", index=" + indexes);
        }
        return element.text();
    }

    public String textBySelector(String selector) {
        return elementBySelect(selector).text();
    }

    public List<String> urlListBySelector(String selector) {
        return extractUrls(elementBySelect(selector));
    }

    public List<String> urlListByIndexes(List<Integer> indexes) {
        return extractUrls(elementByIndex(indexes));
    }

    private List<String> extractUrls(Element element) {
        return element.select("a").eachAttr("abs:href");
    }

    private Element elementByIndex(List<Integer> indexes) {
        Element element = document.body();
        element.select("style").remove();
        for (int index : indexes) {
            if (element.childrenSize() <= index) {
                throw new HtmlDocumentScrapException("uri=" + document.baseUri() + ", index=" + indexes);
            }
            element = element.child(index);
        }
        return element;
    }

    private Element elementBySelect(String selector) {
        Elements elements = extractAllBySelector(selector);
        if (elements.size() != 1) {
            throw new HtmlDocumentScrapException("uri=" + document.baseUri() + ", selector=" + selector);
        }
        return elements.first();
    }

    private Elements extractAllBySelector(String selector) {
        return document.body().select(selector);
    }

}
