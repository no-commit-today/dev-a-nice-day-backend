package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.batch.model.SubscribedTechPost;
import com.nocommittoday.techswipe.domain.rds.subscription.ListCrawling;
import com.nocommittoday.techswipe.domain.rds.subscription.ListCrawlingTechBlogSubscription;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingSelectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCrawlingTechPostService {

    private final TechPostUrlSetService techPostUrlSetService;

    public List<SubscribedTechPost> getTechPostList(final ListCrawlingTechBlogSubscription subscription) {
        final List<SubscribedTechPost> postList = new ArrayList<>();
        final ListCrawling listCrawling = subscription.listCrawling();
        final PostCrawlingSelectors postCrawlingSelectors = subscription.postCrawlingSelectors();
        final TechPostUrlCrawlingIterator iterator = TechPostUrlCrawlingIterator.builder()
                .postLinkElementSelector(listCrawling.selector())
                .postListPageUrl(listCrawling.url())
                .postListPageUrlFormat(listCrawling.pageUrlFormat())
                .build();
        while (iterator.hasNext()) {
            final String postUrl = iterator.next();
            if (techPostUrlSetService.add(postUrl)) {
                final TechPostCrawler crawler = new TechPostCrawler(postUrl);
                final String title = crawler.getTitle(postCrawlingSelectors.title());
                crawler.getDate(postCrawlingSelectors.date());
                final String imageUrl = crawler.getImageUrl();
                final String content = crawler.getContent(postCrawlingSelectors.content());
                postList.add(SubscribedTechPost.builder()
                        .title(title)
                        .imageUrl(imageUrl)
                        .content(content)
                        .url(postUrl)
                        .build()
                );
            }
        }
        return postList;
    }
}
