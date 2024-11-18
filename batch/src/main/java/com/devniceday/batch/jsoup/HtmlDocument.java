package com.devniceday.batch.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.List;
import java.util.Optional;

public class HtmlDocument {

    private final Document document;

    HtmlDocument(Document document) {
        this.document = document;
    }

    public String uri() {
        return document.baseUri();
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
                .filter(imageUrl -> !imageUrl.isBlank())
                .map(this::toAbsoluteUrl)
                .orElse(null);
    }

    public String title() {
        return document.title();
    }

    public String textByIndex(List<Integer> indexes) {
        Element element = elementByIndex(indexes);
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
        return element.select("a").eachAttr("abs:href")
                .stream()
                .filter(url -> !url.isBlank())
                .map(this::toAbsoluteUrl)
                .toList();
    }

    private String toAbsoluteUrl(String url) {
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
        if (uri.isAbsolute()) {
            return url;
        }

        UriComponents baseUriComponents = UriComponentsBuilder.fromHttpUrl(document.baseUri()).build();
        return UriComponentsBuilder.newInstance()
                .scheme(baseUriComponents.getScheme())
                .host(baseUriComponents.getHost())
                .port(baseUriComponents.getPort())
                .build()
                .toUri()
                .resolve(uri)
                .toString();
    }

    public String htmlBySelector(String selector) {
        return elementBySelect(selector).html();
    }

    public String htmlByIndexes(List<Integer> indexes) {
        return elementByIndex(indexes).html();
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
        if (elements.isEmpty()) {
            throw new HtmlDocumentScrapException("uri=" + document.baseUri() + ", selector=" + selector);
        }
        return elements.first();
    }

    private Elements extractAllBySelector(String selector) {
        return document.body().select(selector);
    }

}
