package com.nocommittoday.techswipe.subscription.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.AtomSubscription;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingIndexes;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingNeeds;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawlingSubscription;
import com.nocommittoday.techswipe.subscription.domain.vo.RssSubscription;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@Getter
public class Subscription {

    private final SubscriptionId id;

    @NonNull
    private final TechContentProvider.TechContentProviderId providerId;

    @NonNull
    private final SubscriptionType type;

    private final String rssUrl;

    private final String atomUrl;

    private final ContentCrawlingIndexes contentCrawlingIndexes;

    private final ContentCrawlingNeeds contentCrawlingNeeds;

    private final List<ListCrawling> listCrawlings;

    public RssSubscription toRss() {
        return new RssSubscription(
                rssUrl,
                contentCrawlingIndexes,
                contentCrawlingNeeds
        );
    }

    public AtomSubscription toAtom() {
        return new AtomSubscription(
                atomUrl,
                contentCrawlingIndexes,
                contentCrawlingNeeds
        );
    }

    public List<ListCrawlingSubscription> toListCrawling() {
        return listCrawlings.stream()
                .map(listCrawling ->
                        new ListCrawlingSubscription(contentCrawlingIndexes, listCrawling)
                ).toList();
    }

    public record SubscriptionId(long value) {}
}
