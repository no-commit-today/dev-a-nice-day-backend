package com.nocommittoday.techswipe.infrastructure.feed;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

@Component
public class FeedReader {

    private final XmlClient xmlClient;
    private final SyndFeedBuilder syndFeedInput;

    public FeedReader(XmlClient xmlClient, SyndFeedBuilder syndFeedInput) {
        this.xmlClient = xmlClient;
        this.syndFeedInput = syndFeedInput;
    }

    public Feed read(String url) {
        String xml = xmlClient.get(url)
                .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", " ");
        SyndFeed syndFeed = syndFeedInput.build(xml);
        return convertToFeedResponse(syndFeed);
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
