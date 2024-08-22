package com.nocommittoday.techswipe.domain.subscription;

public class ListScrappingSubscription extends Subscription {

    private final ListScrapping listScrapping;

    private final ContentScrapping contentScrapping;

    ListScrappingSubscription(
            SubscriptionId id,
            ListScrapping listScrapping,
            ContentScrapping contentScrapping
    ) {
        super(id, SubscriptionType.LIST_SCRAPPING);
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
