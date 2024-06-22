package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.client.core.ClientResponse;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

@Slf4j
public class UrlListCrawlingIterator implements Iterator<String> {

    private final DocumentConnector documentConnector;
    private final DocumentElementExtractor documentElementExtractor;
    private final Crawling crawling;
    private String postListPageUrl;
    @Nullable
    private final String postListPageUrlFormat;
    private int page = 1;

    private final Queue<String> postUrls = new LinkedList<>();

    public UrlListCrawlingIterator(
            final DocumentConnector documentConnector,
            final DocumentElementExtractor documentElementExtractor,
            final Crawling crawling,
            final String postListPageUrl,
            @Nullable final String postListPageUrlFormat
    ) {
        this.documentConnector = documentConnector;
        this.documentElementExtractor = documentElementExtractor;
        this.crawling = crawling;
        this.postListPageUrl = postListPageUrl;
        postUrls.addAll(getNextPostUrls());

        this.postListPageUrlFormat = postListPageUrlFormat;

    }

    @Override
    public boolean hasNext() {
        if (postUrls.isEmpty() && isPaginated()) {
            page += 1;
            postListPageUrl = String.format(Objects.requireNonNull(postListPageUrlFormat), page);
            postUrls.addAll(getNextPostUrls());
        }
        return !postUrls.isEmpty();
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return Objects.requireNonNull(postUrls.poll());
    }

    private List<String> getNextPostUrls() {
        final ClientResponse<Document> documentResponse = documentConnector.connect(postListPageUrl);
        if (documentResponse.isNotFound()) {
            log.debug("게시글 목록 페이지가 존재하지 않습니다: {}", postListPageUrl);
            return List.of();
        }
        if (documentResponse.isFailed()) {
            log.error("게시글 목록 페이지를 가져오는 중 오류 발생: {}", postListPageUrl);
            throw documentResponse.getException();
        }
        final Document document = documentResponse.getData();

        if (CrawlingType.INDEX == crawling.type()) {
            return crawlByIndex(document, Objects.requireNonNull(crawling.indexes()));
        } else if (CrawlingType.SELECTOR == crawling.type()) {
            return crawlBySelector(document, Objects.requireNonNull(crawling.selector()));
        } else {
            throw new IllegalArgumentException("지원하지 않는 크롤링 타입입니다: " + crawling.type());

        }
    }

    private List<String> crawlByIndex(final Document document, final List<Integer> postUrlListIndexes) {
        Element element = documentElementExtractor.extractByIndex(document, postUrlListIndexes);
        return new LinkedList<>(element.select("a").eachAttr("abs:href")).stream().toList();
    }

    private List<String> crawlBySelector(final Document document, final String selector) {
        final Element element = documentElementExtractor.extractBySelector(document, selector);
        return new LinkedHashSet<>(element.select("a").eachAttr("abs:href")).stream().toList();
    }

    private boolean isPaginated() {
        return postListPageUrlFormat != null;
    }
}
