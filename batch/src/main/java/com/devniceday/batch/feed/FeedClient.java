package com.devniceday.batch.feed;

import com.devniceday.module.timetracer.TimeTrace;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class FeedClient {

    private final StringClient stringClient;
    private final SyndFeedBuilder syndFeedBuilder;

    public FeedClient(StringClient stringClient, SyndFeedBuilder syndFeedBuilder) {
        this.stringClient = stringClient;
        this.syndFeedBuilder = syndFeedBuilder;
    }

    @TimeTrace
    public Feed get(String url) {
        String xml = stringClient.get(url);
        SyndFeed syndFeed = syndFeedBuilder.build(xml);
        return convertToFeedResponse(Objects.requireNonNull(syndFeed));
    }

    private Feed convertToFeedResponse(SyndFeed syndFeed) {
        return new Feed(
                syndFeed.getLink(),
                syndFeed.getTitle(),
                extractIconUrl(syndFeed),
                syndFeed.getEntries().stream()
                        .map(entry -> new Feed.Entry(
                                entry.getLink(),
                                entry.getTitle(),
                                extractDate(entry),
                                extractContent(entry)
                        ))
                        .toList()

        );
    }

    private String extractIconUrl(SyndFeed syndFeed) {
        return Optional.ofNullable(syndFeed.getIcon())
                .map(SyndImage::getUrl)
                .filter(url -> !url.isBlank())
                .or(() -> Optional.ofNullable(syndFeed.getImage())
                        .map(SyndImage::getUrl)
                        .filter(url -> !url.isBlank())
                )
                .orElse(null);
    }

    private LocalDate extractDate(SyndEntry entry) {
        Date date = entry.getPublishedDate() != null
                ? entry.getPublishedDate() : entry.getUpdatedDate();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private String extractContent(SyndEntry entry) {
        return Optional.ofNullable(entry.getContents())
                .filter(contents -> !contents.isEmpty())
                .flatMap(contents -> contents
                        .stream()
                        .max(Comparator.comparingInt(
                                content -> content.getValue().length()
                        ))
                        .map(SyndContent::getValue)
                ).or(() -> Optional.ofNullable(entry.getDescription())
                        .map(SyndContent::getValue)
                ).orElse(null);
    }
}
