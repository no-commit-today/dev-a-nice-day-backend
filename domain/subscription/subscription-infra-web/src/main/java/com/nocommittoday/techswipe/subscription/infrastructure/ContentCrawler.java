package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.vo.Crawling;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ContentCrawler {

    private final Document document;

    public ContentCrawler(final DocumentConnector connector, final String url) {
        this.document = connector.connect(url).get();
        this.document.select("style").remove();
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
                .orElse(null);
    }

    public String get(final Crawling crawling) {
        return crawl(crawling).html();
    }

    public String getText(final Crawling crawling) {
        final Element element = crawl(crawling);
        if (element.children().isEmpty()) {
            throw new CrawlingException(document.baseUri(), crawling, element.html());
        }
        return element.text();
    }

    private Element crawl(final Crawling crawling) {
        if (CrawlingType.INDEX == crawling.type()) {
            return crawlByIndex(Objects.requireNonNull(crawling.indexes()));
        } else if (CrawlingType.SELECTOR == crawling.type()) {
            return crawlBySelector(Objects.requireNonNull(crawling.selector()));
        } else {
            throw new UnsupportedCrawlingTypeException(crawling.type());
        }
    }

    private Element crawlByIndex(final List<Integer> indexes) {
        Element element = document.body();
        for (int index : indexes) {
            element = element.child(index);
        }
        return element;
    }

    private Element crawlBySelector(final String selector) {
        return document.body().select(selector).first();
    }

}
