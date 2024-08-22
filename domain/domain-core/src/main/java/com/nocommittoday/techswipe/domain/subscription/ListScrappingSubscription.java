package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

public class ListScrappingSubscription extends Subscription {

    private final ListScrapping listScrapping;

    private final ContentScrapping contentScrapping;

    ListScrappingSubscription(
            SubscriptionId id,
            TechContentProviderId providerId,
            ListScrapping listScrapping,
            ContentScrapping contentScrapping
    ) {
        super(id, SubscriptionType.LIST_SCRAPPING, providerId);
        this.listScrapping = listScrapping;
        this.contentScrapping = contentScrapping;
    }

    @Override
    public boolean isInitRequired() {
        return true;
    }

    public ListScrapping getListScrapping() {
        return listScrapping;
    }

    public ContentScrapping getContentScrapping() {
        return contentScrapping;
    }
}
