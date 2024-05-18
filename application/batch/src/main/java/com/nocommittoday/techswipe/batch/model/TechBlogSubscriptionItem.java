package com.nocommittoday.techswipe.batch.model;

import com.nocommittoday.techswipe.domain.rds.subscription.ListCrawling;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingNeeds;
import com.nocommittoday.techswipe.domain.rds.subscription.PostCrawlingSelectors;
import com.nocommittoday.techswipe.domain.rds.subscription.TechBlogSubscription;
import com.nocommittoday.techswipe.domain.rds.subscription.TechBlogSubscriptionType;

import java.util.List;

public record TechBlogSubscriptionItem(
        String blogUrl,
        TechBlogSubscriptionType type,
        String rssUrl,
        String atomUrl,
        PostCrawlingSelectors postCrawlingSelectors,
        PostCrawlingNeeds postCrawlingNeeds,
        List<ListCrawling> listCrawlings
) {
    public TechBlogSubscription toDomain(final long id) {
        return TechBlogSubscription.builder()
                .techBlogId(id)
                .type(type)
                .rssUrl(rssUrl)
                .atomUrl(atomUrl)
                .postCrawlingSelectors(postCrawlingSelectors)
                .postCrawlingNeeds(postCrawlingNeeds)
                .listCrawlings(listCrawlings)
                .build();
    }
}
