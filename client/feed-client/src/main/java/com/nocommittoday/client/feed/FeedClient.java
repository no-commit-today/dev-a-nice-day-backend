package com.nocommittoday.client.feed;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class FeedClient {

    private final XmlClient xmlClient;
    private final SyndFeedBuilder syndFeedBuilder;

    public ClientResponse<FeedResponse> get(final String url) {
        try {
            final String xml = xmlClient.get(url)
                    .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", " ");
            final SyndFeed syndFeed = syndFeedBuilder.build(xml);
            return ClientResponse.success(convertToFeedResponse(syndFeed));
        } catch (final Exception e) {
            log.error("[FeedClient] url={}", url, e);
            return ClientResponse.failed(e);
        }
    }

    private FeedResponse convertToFeedResponse(final SyndFeed syndFeed) {
        return new FeedResponse(
                syndFeed.getLink(),
                syndFeed.getTitle(),
                extractIconUrl(syndFeed),
                syndFeed.getEntries().stream()
                        .map(entry -> new FeedResponse.Entry(
                                entry.getLink(),
                                entry.getTitle(),
                                extractDate(entry),
                                extractContent(entry)
                        ))
                        .toList()

        );
    }

    private static String extractIconUrl(final SyndFeed syndFeed) {
        return Optional.ofNullable(syndFeed.getIcon())
                .or(() -> Optional.ofNullable(syndFeed.getImage()))
                .map(SyndImage::getUrl)
                .orElse(null);
    }

    private static LocalDate extractDate(final SyndEntry entry) {
        final Date date = entry.getPublishedDate() != null
                ? entry.getPublishedDate() : entry.getUpdatedDate();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static String extractContent(final SyndEntry entry) {
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
