package com.nocommittoday.techswipe.batch.service;

import jakarta.annotation.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TechPostHtmlIndexCrawler {

    private static final Logger log = LoggerFactory.getLogger(TechPostHtmlIndexCrawler.class);

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

    public TechPostHtmlIndexCrawler(final String url) {
        try {
            this.document = Jsoup.connect(url).get();
            document.select("style").remove();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(dateText, formatter);
            } catch (final DateTimeParseException e) {
                log.debug("날짜 형식 변환 실패: {}", dateText, e);
            }
        }
        if (dateText.endsWith("days ago")) {
            int days = Integer.parseInt(dateText.split(" ")[0]);
            return LocalDate.now().minusDays(days);
        }

        throw new IllegalArgumentException("날짜 형식 변환 실패: " + dateText);
    }

    public String getContent(final List<Integer> indexes) {
        return getText(indexes);
    }

    public String cleanHtmlTag(final String html) {
        return Jsoup.parse(html).text();
    }

}
