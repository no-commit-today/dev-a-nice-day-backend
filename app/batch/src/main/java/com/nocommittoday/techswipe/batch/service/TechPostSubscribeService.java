package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.batch.model.SubscribedTechPost;
import com.nocommittoday.techswipe.batch.model.SubscribedTechPostList;
import com.nocommittoday.techswipe.domain.rds.subscription.TechBlogSubscription;
import com.nocommittoday.techswipe.domain.rds.subscription.TechBlogSubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechPostSubscribeService {

    private final ListCrawlingTechPostService listCrawlingTechPostService;
    private final RssTechPostService rssTechPostService;
    private final AtomTechPostService atomTechPostService;

    public SubscribedTechPostList getTechPostList(final TechBlogSubscription subscription) {
        final List<SubscribedTechPost> techPostList = doSubscribe(subscription);
        return new SubscribedTechPostList(subscription.techBlogId(), techPostList);
    }

    private List<SubscribedTechPost> doSubscribe(final TechBlogSubscription subscription) {
        if (TechBlogSubscriptionType.LIST_CRAWLING == subscription.type()) {
            return subscription.toListCrawling().stream()
                    .map(listCrawlingTechPostService::getTechPostList)
                    .flatMap(List::stream)
                    .toList();
        } else if (TechBlogSubscriptionType.RSS == subscription.type()) {
            return rssTechPostService.getTechPostList(subscription.toRss());
        } else if (TechBlogSubscriptionType.ATOM == subscription.type()) {
            return atomTechPostService.getTechPostList(subscription.toAtom());
        }
        throw new IllegalArgumentException("Not supported subscription type");
    }
}
