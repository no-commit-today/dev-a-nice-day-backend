package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.batch.model.SubscribedTechPost;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingNeeds;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingIndexes;
import com.nocommittoday.techswipe.domain.rds.subscription.RssTechBlogSubscription;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class RssTechPostService {

    private final RestTemplate restTemplate;
    private final TechPostUrlSetService techPostUrlSetService;

    public RssTechPostService(
            final RestTemplateBuilder restTemplateBuilder,
            final TechPostUrlSetService techPostUrlSetService
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.techPostUrlSetService = techPostUrlSetService;
    }

    public List<SubscribedTechPost> getTechPostList(final RssTechBlogSubscription subscription) {
        final String xmlString = Objects.requireNonNull(restTemplate.getForObject(subscription.url(), String.class))
                .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", " ");
        final Channel channel = mapToChannel(xmlString);
        final PostCrawlingNeeds crawlingNeeds = subscription.postCrawlingNeeds();
        final PostCrawlingIndexes crawlingIndexes = subscription.postCrawlingIndexes();
        return channel.getItems().stream()
                .filter(item -> techPostUrlSetService.add(item.getLink()))
                .map(item -> {
                    log.debug("item: {}", item.getLink());
                    final TechPostHtmlIndexCrawler crawler = new TechPostHtmlIndexCrawler(item.getLink());
                    final String title = crawlingNeeds.title() ?
                            crawler.getTitle(crawlingIndexes.title()) : item.getTitle();
                    final String imageUrl = crawler.getImageUrl();
                    final LocalDate publishedDate = crawlingNeeds.date() ?
                            crawler.getDate(crawlingIndexes.date()) :
                            item.getPubDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    final String content = crawlingNeeds.content() ?
                            crawler.getContent(crawlingIndexes.content()) :
                            crawler.cleanHtmlTag(item.getContent().getValue());

                    return SubscribedTechPost.builder()
                            .url(item.getLink())
                            .title(title)
                            .imageUrl(imageUrl)
                            .publishedDate(publishedDate)
                            .content(content)
                            .build();
                }).toList();
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
