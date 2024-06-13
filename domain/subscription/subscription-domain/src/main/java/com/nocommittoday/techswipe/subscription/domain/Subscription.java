package com.nocommittoday.techswipe.subscription.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.AtomSubscription;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawlingSubscription;
import com.nocommittoday.techswipe.subscription.domain.vo.RssSubscription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;


@RequiredArgsConstructor
@Getter
public class Subscription {

    private final SubscriptionId id;

    private final TechContentProvider.TechContentProviderId providerId;

    private final SubscriptionType type;

    private final SubscriptionInitType initType;

    @Nullable
    private final String rssUrl;

    @Nullable
    private final String atomUrl;

    private final ContentCrawling contentCrawling;

    private final List<ListCrawling> listCrawlings;

    public RssSubscription toRss() {
        return new RssSubscription(
                rssUrl,
                contentCrawling
        );
    }

    public AtomSubscription toAtom() {
        return new AtomSubscription(
                atomUrl,
                contentCrawling
        );
    }

    public List<ListCrawlingSubscription> toListCrawling() {
        return listCrawlings.stream()
                .map(listCrawling ->
                        new ListCrawlingSubscription(listCrawling, contentCrawling)
                ).toList();
    }

    public record SubscriptionId(long value) {}
}
