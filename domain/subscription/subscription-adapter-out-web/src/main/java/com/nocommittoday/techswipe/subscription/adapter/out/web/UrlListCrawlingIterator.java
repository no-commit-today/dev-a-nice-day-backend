package com.nocommittoday.techswipe.subscription.adapter.out.web;

import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.vo.Crawling;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

@Slf4j
class UrlListCrawlingIterator implements Iterator<String> {

    private final Crawling crawling;
    private String postListPageUrl;
    private final boolean paginated;
    @Nullable
    private final String postListPageUrlFormat;
    private int page = 1;

    private final Queue<String> postUrls = new LinkedList<>();

    public UrlListCrawlingIterator(
            final Crawling crawling,
            final String postListPageUrl,
            @Nullable final String postListPageUrlFormat
    ) {
        this.crawling = crawling;
        this.postListPageUrl = postListPageUrl;
        postUrls.addAll(getNextPostUrls());

        this.paginated = postListPageUrlFormat != null;
        this.postListPageUrlFormat = postListPageUrlFormat;

    }

    @Override
    public boolean hasNext() {
        if (postUrls.isEmpty() && paginated) {
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
        Document document;
        try {
            document = Jsoup.connect(postListPageUrl).get();
            document.select("style").remove();
        } catch (final HttpStatusException e) {
            if (e.getStatusCode() == 404) {
                log.debug("게시글 목록 페이지가 존재하지 않습니다: {}", postListPageUrl);
                return List.of();
            }
            throw new UncheckedIOException(e);
        } catch (final IOException e) {
            log.error("게시글 목록 페이지를 가져오는 중 오류 발생: {}", postListPageUrl);
            throw new UncheckedIOException(e);
        }

        if (CrawlingType.INDEX == crawling.type()) {
            return crawlByIndex(document, Objects.requireNonNull(crawling.indexes()));
        } else if (CrawlingType.SELECTOR == crawling.type()) {
            return crawlBySelector(document, Objects.requireNonNull(crawling.selector()));
        } else {
            throw new IllegalArgumentException("지원하지 않는 크롤링 타입입니다: " + crawling.type());

        }
    }

    private List<String> crawlByIndex(final Document document, final List<Integer> postUrlListIndexes) {
        Element element = document.body();
        log.debug("{} [body]\n{}", document.baseUri(), element.html());
        for (int i = 0; i < postUrlListIndexes.size(); i++) {
            final int index = postUrlListIndexes.get(i);
            element = element.child(index);
            log.debug("{} [child{} index{}]\n{}", document.baseUri(), i, index, element.html());
        }

        return new LinkedList<>(element.select("a").eachAttr("abs:href")).stream().toList();
    }

    private List<String> crawlBySelector(final Document document, final String selector) {
        final Element element = Objects.requireNonNull(document.body().select(selector).first());
        return new LinkedHashSet<>(element.select("a").eachAttr("abs:href")).stream().toList();
    }
}