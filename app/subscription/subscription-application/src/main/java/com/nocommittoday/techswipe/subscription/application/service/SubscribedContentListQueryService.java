package com.nocommittoday.techswipe.subscription.application.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscribedContentListQuery;
import com.nocommittoday.techswipe.subscription.application.port.out.AtomContentReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.out.ListCrawlingContentReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.out.RssContentReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.out.SubscriptionReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class SubscribedContentListQueryService implements SubscribedContentListQuery {

    private final SubscriptionReaderPort subscriptionReaderPort;
    private final RssContentReaderPort rssContentReaderPort;
    private final AtomContentReaderPort atomContentReaderPort;
    private final ListCrawlingContentReaderPort listCrawlingContentReaderPort;

    @Override
    public List<SubscribedContent> getList(final TechContentProvider.TechContentProviderId providerId) {
        final Subscription subscription = subscriptionReaderPort.getByProviderId(providerId);
        return getSubscribedContentList(subscription);
    }

    private List<SubscribedContent> getSubscribedContentList(final Subscription subscription) {
        if (SubscriptionType.LIST_CRAWLING == subscription.getType()) {
            return subscription.toListCrawling().stream()
                    .map(listCrawlingContentReaderPort::getList)
                    .flatMap(List::stream)
                    .toList();
        } else if (SubscriptionType.RSS == subscription.getType()) {
            return rssContentReaderPort.getList(subscription.toRss());
        } else if (SubscriptionType.ATOM == subscription.getType()) {
            return atomContentReaderPort.getList(subscription.toAtom());
        }
        throw new IllegalArgumentException("지원하지 않는 타입: " + subscription.getType());
    }
}
