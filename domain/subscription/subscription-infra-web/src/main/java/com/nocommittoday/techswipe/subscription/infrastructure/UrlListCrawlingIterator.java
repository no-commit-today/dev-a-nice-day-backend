package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.infrastructure.exception.DocumentConnectException;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

@Slf4j
public class UrlListCrawlingIterator implements Iterator<String> {

    private final DocumentConnector documentConnector;
    private final ListCrawling listCrawling;
    private int page = 1;

    private final Queue<String> urls = new LinkedList<>();

    public UrlListCrawlingIterator(
            DocumentConnector documentConnector,
            ListCrawling listCrawling
    ) {
        this.documentConnector = documentConnector;
        this.listCrawling = listCrawling;
        urls.addAll(getUrls(this.listCrawling.url()));
    }

    @Override
    public boolean hasNext() {
        if (urls.isEmpty() && listCrawling.isPaginated()) {
            page += 1;
            urls.addAll(getUrls(listCrawling.getPageUrl(page)));
        }
        return !urls.isEmpty();
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return Objects.requireNonNull(urls.poll());
    }

    private List<String> getUrls(String listPageUrl) {
        ClientResponse<DocumentCrawler> documentResponse = documentConnector.connect(listPageUrl);
        if (documentResponse.isNotFound()) {
            if (page == 1) {
                throw new DocumentConnectException(documentResponse.getException());
            }
            log.debug("게시글 목록 페이지가 존재하지 않습니다: {}", listPageUrl);
            return List.of();
        }
        if (documentResponse.isFailed()) {
            throw new DocumentConnectException(documentResponse.getException());
        }
        DocumentCrawler documentCrawler = documentResponse.get();
        return documentCrawler.getUrlList(listCrawling.crawling()).stream()
                .filter(listCrawling::isContentUrl)
                .toList();
    }

}
