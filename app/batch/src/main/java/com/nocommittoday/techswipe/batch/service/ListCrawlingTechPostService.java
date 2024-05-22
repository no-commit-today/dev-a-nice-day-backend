package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.batch.model.SubscribedTechPost;
import com.nocommittoday.techswipe.domain.rds.subscription.ListCrawling;
import com.nocommittoday.techswipe.domain.rds.subscription.ListCrawlingTechBlogSubscription;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingIndexes;
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
        final PostCrawlingIndexes postCrawlingIndexes = subscription.postCrawlingIndexes();
        final TechPostUrlListHtmlIndexCrawlingIterator iterator = TechPostUrlListHtmlIndexCrawlingIterator.builder()
                .postUrlListIndexes(listCrawling.indexes())
                .postListPageUrl(listCrawling.url())
                .postListPageUrlFormat(listCrawling.pageUrlFormat())
                .build();
        while (iterator.hasNext()) {
            final String postUrl = iterator.next();
            if (techPostUrlSetService.add(postUrl)) {
                final TechPostHtmlIndexCrawler crawler = new TechPostHtmlIndexCrawler(postUrl);
                final String title = crawler.getTitle(postCrawlingIndexes.title());
                crawler.getDate(postCrawlingIndexes.date());
                final String imageUrl = crawler.getImageUrl();
                final String content = crawler.getContent(postCrawlingIndexes.content())
                        .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", " ");
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
