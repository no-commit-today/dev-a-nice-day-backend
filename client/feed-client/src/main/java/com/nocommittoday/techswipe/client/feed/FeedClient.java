package com.nocommittoday.techswipe.client.feed;

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

    public ClientResponse<FeedResponse> get(String url) {
        try {
            String xml = xmlClient.get(url)
                    .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", " ");
            SyndFeed syndFeed = syndFeedBuilder.build(xml);
            return ClientResponse.success(convertToFeedResponse(syndFeed));
        } catch (Exception e) {
            log.error("[FeedClient] url={}", url, e);
            return ClientResponse.failed(e);
        }
    }

    private FeedResponse convertToFeedResponse(SyndFeed syndFeed) {
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

    private static String extractIconUrl(SyndFeed syndFeed) {
        return Optional.ofNullable(syndFeed.getIcon())
                .map(SyndImage::getUrl)
                .filter(url -> !url.isBlank())
                .or(() -> Optional.ofNullable(syndFeed.getImage())
                        .map(SyndImage::getUrl)
                        .filter(url -> !url.isBlank())
                )
                .orElse(null);
    }

    private static LocalDate extractDate(SyndEntry entry) {
        Date date = entry.getPublishedDate() != null
                ? entry.getPublishedDate() : entry.getUpdatedDate();
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static String extractContent(SyndEntry entry) {
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
