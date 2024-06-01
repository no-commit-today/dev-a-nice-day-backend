package com.nocommittoday.techswipe.subscription.adapter.out.web;

import com.nocommittoday.techswipe.subscription.application.port.out.RssContentReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingIndexes;
import com.nocommittoday.techswipe.subscription.domain.vo.RssSubscription;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class RssContentReaderAdapter implements RssContentReaderPort {

    private final RestTemplate restTemplate;

    @Override
    public List<SubscribedContent> getList(
            final RssSubscription subscription, final LocalDate date
    ) {
        final String xmlString = Objects.requireNonNull(restTemplate.getForObject(subscription.url(), String.class))
                .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", " ");
        final Channel channel = mapToChannel(xmlString);
        final List<SubscribedContent> result = new ArrayList<>();
        for (Item item : channel.getItems()) {
            final LocalDate pubDate = item.getPubDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.isAfter(pubDate)) {
                break;
            }

            final IndexContentCrawler crawler = new IndexContentCrawler(item.getLink());
            final String imageUrl = crawler.getImageUrl();
            final String title = Optional.of(subscription)
                    .map(RssSubscription::contentCrawlingIndexes)
                    .map(ContentCrawlingIndexes::title)
                    .map(crawler::getTitle)
                    .orElse(item.getTitle());
            final LocalDate publishedDate = Optional.of(subscription)
                    .map(RssSubscription::contentCrawlingIndexes)
                    .map(ContentCrawlingIndexes::date)
                    .map(crawler::getDate)
                    .orElse(pubDate);
            final String content = Optional.of(subscription)
                    .map(RssSubscription::contentCrawlingIndexes)
                    .map(ContentCrawlingIndexes::content)
                    .map(crawler::getContent)
                    .orElse(crawler.cleanHtmlTag(item.getDescription().getValue()));

            result.add(new SubscribedContent(
                    item.getLink(),
                    title,
                    imageUrl,
                    publishedDate,
                    content
            ));
        }
        return Collections.unmodifiableList(result);
    }

    private Channel mapToChannel(String xmlString) {
        final WireFeedInput input = new WireFeedInput();
        try {
            return (Channel) input.build(new StringReader(xmlString));
        } catch (final FeedException e) {
            throw new IllegalStateException(e);
        }
    }
}