package com.nocommittoday.techswipe.subscription.adapter.out.web;

import com.nocommittoday.techswipe.subscription.application.port.out.AtomContentReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.vo.AtomSubscription;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingIndexes;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class AtomContentReaderAdapter implements AtomContentReaderPort {

    private final RestTemplate restTemplate;

    @Override
    public List<SubscribedContent> getList(
            final AtomSubscription subscription, final LocalDate date
    ) {
        final Feed feed = Objects.requireNonNull(restTemplate.getForObject(subscription.url(), Feed.class));
        final List<SubscribedContent> result = new ArrayList<>();
        for (Entry item : feed.getEntries()) {
            if (date.isAfter(getDate(item))) {
                break;
            }
            final IndexContentCrawler crawler = new IndexContentCrawler(item.getId());
            final String imageUrl = crawler.getImageUrl();
            final String title = Optional.of(subscription)
                    .map(AtomSubscription::contentCrawlingIndexes)
                    .map(ContentCrawlingIndexes::title)
                    .map(crawler::getTitle)
                    .orElse(item.getTitle());

            final LocalDate publishedDate = Optional.of(subscription)
                    .map(AtomSubscription::contentCrawlingIndexes)
                    .map(ContentCrawlingIndexes::date)
                    .map(crawler::getDate)
                    .orElseGet(() -> getDate(item));

            final String content = Optional.of(subscription)
                    .map(AtomSubscription::contentCrawlingIndexes)
                    .map(ContentCrawlingIndexes::content)
                    .map(crawler::getContent)
                    .orElseGet(() -> item.getContents()
                            .stream()
                            .map(Content::getValue).collect(Collectors.joining("\n\n"))
                    );

            result.add(new SubscribedContent(
                    item.getId(),
                    title,
                    imageUrl,
                    publishedDate,
                    content
            ));
        }

        return Collections.unmodifiableList(result);
    }

    private LocalDate getDate(final Entry entry) {
        return Optional.ofNullable(entry.getIssued())
                .or(() -> Optional.ofNullable(entry.getCreated()))
                .or(() -> Optional.ofNullable(entry.getPublished()))
                .or(() -> Optional.ofNullable(entry.getUpdated()))
                .orElseThrow()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}