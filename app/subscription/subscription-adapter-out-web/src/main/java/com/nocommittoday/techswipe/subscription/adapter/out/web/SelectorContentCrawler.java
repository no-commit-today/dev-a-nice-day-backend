package com.nocommittoday.techswipe.subscription.adapter.out.web;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
class SelectorContentCrawler extends AbstractContentCrawler{

    public SelectorContentCrawler(final String url) {
        super(url);
    }

    public String getText(final String selector) {
        final String text = Optional.of(
                        document.body().select(selector)
                ).map(Elements::first)
                .map(Element::text)
                .orElse(null);
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("텍스트 추출 실패. selector = " + selector);
        }
        return text;
    }

    public String getTitle(@Nullable final String selector) {
        if (selector == null) {
            return document.title();
        }
        return getText(selector);
    }

    public LocalDate getDate(final String selector) {
        final String dateText = getText(selector);
        return parseDate(dateText);
    }

    public String getContent(final String selector) {
        final Element element = document.body().select(selector).first();
        Assert.notNull(element, "본문 추출 실패. url =" + document.baseUri() + ", selector = " + selector);
        return element.text();
    }

}
