package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.infrastructure.exception.CrawlingException;
import com.nocommittoday.techswipe.subscription.infrastructure.exception.UnsupportedCrawlingTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DocumentCrawler {

    private static final Logger log = LoggerFactory.getLogger(DocumentCrawler.class);

    private final Document document;

    public DocumentCrawler(Document document) {
        this.document = document;
    }

    @Nullable
    public String getImageUrl() {
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

    public String get(Crawling crawling) {
        return crawl(crawling).html();
    }

    public String getText(Crawling crawling) {
        Element element = crawl(crawling);
        if (!element.children().isEmpty()) {
            throw new CrawlingException(document.baseUri(), crawling);
        }
        return element.text();
    }

    public List<String> getUrlList(Crawling crawling) {
        return new LinkedHashSet<>(
                crawl(crawling).select("a").eachAttr("abs:href")
        ).stream().toList();
    }

    private Element crawl(Crawling crawling) {
        try {
            if (CrawlingType.INDEX == crawling.type()) {
                return extractByIndex(Objects.requireNonNull(crawling.indexes()));
            } else if (CrawlingType.SELECTOR == crawling.type()) {
                return extractAllBySelector(Objects.requireNonNull(crawling.selector())).first();
            }
        } catch (Exception ex) {
            throw new CrawlingException(document.baseUri(), crawling, ex);
        }

        throw new UnsupportedCrawlingTypeException(crawling.type());
    }

    private Element extractByIndex(List<Integer> indexes) {
        Element element = document.body();
        element.select("style").remove();
        log.debug("인덱스로 추출 시작. url={}", document.baseUri());
        for (int i = 0; i < indexes.size(); i++) {
            int index = indexes.get(i);
            if (log.isTraceEnabled()) {
                log.trace("url={}, indexes[{}]={} 추출. indexes={}. \n{}",
                        document.baseUri(), i, index, indexes, element.html());
            }
            element = element.child(index);
        }
        return element;
    }

    private Elements extractAllBySelector(String selector) {
        log.debug("셀렉터로 추출 시작. url={}", document.baseUri());
        Elements elements = document.body().select(selector);
        if (log.isTraceEnabled()) {
            log.trace("url={}, selector={}, \n{}",
                    document.baseUri(), selector, elements.html());
        }
        return elements;
    }
}
