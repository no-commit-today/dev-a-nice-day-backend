package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
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

    private final Queue<String> postUrls = new LinkedList<>();

    public UrlListCrawlingIterator(
            final DocumentConnector documentConnector,
            final ListCrawling listCrawling
    ) {
        this.documentConnector = documentConnector;
        this.listCrawling = listCrawling;
        postUrls.addAll(getUrls(listCrawling.url()));
    }

    @Override
    public boolean hasNext() {
        if (postUrls.isEmpty() && listCrawling.isPaginated()) {
            page += 1;
            postUrls.addAll(getUrls(listCrawling.getPageUrl(page)));
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

    private List<String> getUrls(final String listPageUrl) {
        final ClientResponse<DocumentCrawler> documentResponse = documentConnector.connect(listPageUrl);
        if (documentResponse.isNotFound()) {
            log.debug("게시글 목록 페이지가 존재하지 않습니다: {}", listPageUrl);
            return List.of();
        }
        final DocumentCrawler documentCrawler = documentResponse.get();
        return documentCrawler.getUrlList(listCrawling.crawling());
    }

}
