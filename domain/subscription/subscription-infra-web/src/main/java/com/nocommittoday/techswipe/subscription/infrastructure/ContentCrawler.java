package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
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
    private final DocumentElementExtractor documentElementExtractor;
    private final HtmlTagCleaner htmlTagCleaner;

    public ContentCrawler(
            final DocumentElementExtractor documentElementExtractor,
            final DocumentConnector connector,
            final String url,
            final HtmlTagCleaner htmlTagCleaner
    ) {
        this.documentElementExtractor = documentElementExtractor;
        this.document = connector.connect(url).get();
        this.htmlTagCleaner = htmlTagCleaner;
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
        if (!element.children().isEmpty()) {
            throw new CrawlingException(document.baseUri(), crawling, element.html());
        }
        return element.text();
    }

    public String getCleaned(final Crawling crawling) {
        return htmlTagCleaner.clean(crawl(crawling));
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
        return documentElementExtractor.extractByIndex(document, indexes);
    }

    private Element crawlBySelector(final String selector) {
        return documentElementExtractor.extractBySelector(document, selector);
    }

}
