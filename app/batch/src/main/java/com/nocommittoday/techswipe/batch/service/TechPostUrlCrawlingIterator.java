package com.nocommittoday.techswipe.batch.service;

import jakarta.annotation.Nullable;
import lombok.Builder;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class TechPostUrlCrawlingIterator implements Iterator<String> {

    private static final Logger log = LoggerFactory.getLogger(TechPostUrlCrawlingIterator.class);

    private final String postLinkElementSelector;
    private String postListPageUrl;
    private final boolean paginated;
    @Nullable private final String postListPageUrlFormat;
    private int page = 1;

    private final Queue<String> postUrls = new LinkedList<>();

    @Builder
    public TechPostUrlCrawlingIterator(
            final String postLinkElementSelector,
            final String postListPageUrl,
            @Nullable final String postListPageUrlFormat
    ) {
        this.postLinkElementSelector = postLinkElementSelector;
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
        return postUrls.poll();
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

        final Element element = Objects.requireNonNull(document.body().select(postLinkElementSelector).first());

        final Set<String> urlSet = new LinkedHashSet<>(element.select("a").eachAttr("abs:href"));
        return urlSet.stream().toList();
    }

}
