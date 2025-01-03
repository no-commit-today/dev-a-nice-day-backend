package com.devniceday.batch.domain;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LocalDateParser {

    private final Clock clock;

    public LocalDateParser(Clock clock) {
        this.clock = clock;
    }

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

    private static final String DAYS_AGO_PARAM = "daysAgo";
    private static final Pattern DAYS_AGO_PATTERN = Pattern.compile("(?<" + DAYS_AGO_PARAM + ">\\d+) days ago");

    public LocalDate parse(String text) {
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(text, formatter);
            } catch (DateTimeParseException ignored) {
                // ignore
            }
        }

        Matcher daysAgoMatcher = DAYS_AGO_PATTERN.matcher(text);
        if (daysAgoMatcher.matches()) {
            int daysAgo = Integer.parseInt(daysAgoMatcher.group(DAYS_AGO_PARAM));
            return LocalDate.now(clock).minusDays(daysAgo);
        }

        throw new IllegalArgumentException("지원되지 않는 날짜 형식: " + text);
    }
}
