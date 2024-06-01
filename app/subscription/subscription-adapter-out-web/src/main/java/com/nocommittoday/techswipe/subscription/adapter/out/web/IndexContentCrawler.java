package com.nocommittoday.techswipe.subscription.adapter.out.web;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Slf4j
class IndexContentCrawler extends AbstractContentCrawler {

    public IndexContentCrawler(final String url) {
        super(url);
    }

    public String getText(final List<Integer> indexes) {
        Element element = document.body();
        for (int index : indexes) {
            element = element.child(index);
        }
        final String text = element.text();
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("텍스트 추출 실패. url=" + document.baseUri() + " ,indexes=" + indexes);
        }
        return text;
    }

    public String getTitle(@Nullable final List<Integer> indexes) {
        if (indexes == null) {
            return document.title();
        }
        return getText(indexes);
    }

    public LocalDate getDate(final List<Integer> indexes) {
        final String dateText = getText(indexes);
        return parseDate(dateText);
    }

    public String getContent(final List<Integer> indexes) {
        return getText(indexes);
    }

}
