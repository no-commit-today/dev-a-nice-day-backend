package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.vo.Crawling;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
class ContentCrawler {

    private static final List<DateTimeFormatter> FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy. M. d"),
            DateTimeFormatter.ofPattern("yy.MM.dd"),
            DateTimeFormatter.ofPattern("yyyy.M.d."),
            DateTimeFormatter.ofPattern("yyyy. M. d."),
            DateTimeFormatter.ofPattern("yyyy년 M월 d일"),
            DateTimeFormatter.ofPattern("dd M월 yyyy"),
            DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("MMM.d.yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.ENGLISH)
    );

    private final Document document;

    public ContentCrawler(final DocumentConnector connector) {
        this.document = connector.connect();
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

    public String getText(final Crawling crawling) {
        if (CrawlingType.INDEX == crawling.type()) {
            return crawlByIndex(Objects.requireNonNull(crawling.indexes()));
        } else if (CrawlingType.SELECTOR == crawling.type()) {
            return crawlBySelector(Objects.requireNonNull(crawling.selector()));
        } else {
            throw new IllegalArgumentException("지원하지 않는 크롤링 타입: " + crawling.type());
        }
    }

    private String crawlByIndex(final List<Integer> indexes) {
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

    private String crawlBySelector(final String selector) {
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

    public LocalDate getDate(final Crawling crawling) {
        final String dateText = getText(crawling);
        return parseDate(dateText);
    }

    public LocalDate parseDate(final String text) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(text, formatter);
            } catch (final DateTimeParseException ignored) {
                // ignore
            }
        }
        if (text.endsWith("days ago")) {
            int days = Integer.parseInt(text.split(" ")[0]);
            return LocalDate.now().minusDays(days);
        }

        throw new IllegalArgumentException("날짜 형식 변환 실패: " + text);
    }

    public String getTitle(final Crawling crawling) {
        return getText(crawling);
    }

    public String getContent(final Crawling crawling) {
        return getText(crawling);
    }

    public String cleanHtmlTag(final String html) {
        return Jsoup.parse(html).text();
    }
}
