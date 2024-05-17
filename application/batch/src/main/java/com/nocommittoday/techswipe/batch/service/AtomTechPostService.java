package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.batch.model.SubscribedTechPost;
import com.nocommittoday.techswipe.domain.rds.subscription.AtomTechBlogSubscription;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingNeeds;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingSelectors;
import com.rometools.rome.feed.atom.Feed;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Service
public class AtomTechPostService {

    private final RestTemplate restTemplate;
    private final TechPostUrlSetService techPostUrlSetService;
    private final HtmlTagCleaner htmlTagCleaner;

    public AtomTechPostService(
            final RestTemplateBuilder restTemplateBuilder,
            final TechPostUrlSetService techPostUrlSetService,
            final HtmlTagCleaner htmlTagCleaner
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.techPostUrlSetService = techPostUrlSetService;
        this.htmlTagCleaner = htmlTagCleaner;
    }

    public List<SubscribedTechPost> getTechPostList(final AtomTechBlogSubscription subscription) {
        final Feed feed = Objects.requireNonNull(restTemplate.getForObject(subscription.url(), Feed.class));
        final PostCrawlingNeeds postCrawlingNeeds = subscription.postCrawlingNeeds();
        final PostCrawlingSelectors postCrawlingSelectors = subscription.postCrawlingSelectors();
        return feed.getEntries().stream()
                .filter(entry -> techPostUrlSetService.add(entry.getId()))
                .map(entry -> {
                    // TODO 필요에 따라 연결하도록
                    final TechPostCrawler crawler = new TechPostCrawler(entry.getId());
                    final String title = postCrawlingNeeds.title() ?
                            crawler.getTitle(postCrawlingSelectors.title()) : entry.getTitle();
                    final String imageUrl = crawler.getImageUrl();
                    final LocalDate publishedDate = postCrawlingNeeds.date() ?
                            crawler.getDate(postCrawlingSelectors.date()) :
                            entry.getUpdated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    final String content = postCrawlingNeeds.content() ?
                            crawler.getContent(postCrawlingSelectors.content()) :
                            htmlTagCleaner.clean(entry.getSummary().getValue());

                    return SubscribedTechPost.builder()
                            .url(entry.getId())
                            .title(title)
                            .imageUrl(imageUrl)
                            .publishedDate(publishedDate)
                            .content(content)
                            .build();
                })
                .toList();
    }

}
