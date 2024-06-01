package com.nocommittoday.techswipe.subscription.adapter.out.web;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
abstract class AbstractContentCrawler {

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

    protected final Document document;

    protected AbstractContentCrawler(final String url) {
        try {
            this.document = Jsoup.connect(url).get();
        } catch (final IOException e) {
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

    protected LocalDate parseDate(final String text) {
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

    public String cleanHtmlTag(final String html) {
        return Jsoup.parse(html).text();
    }

}
