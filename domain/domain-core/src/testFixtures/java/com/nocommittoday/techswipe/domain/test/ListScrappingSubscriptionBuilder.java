package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.ContentScrapping;
import com.nocommittoday.techswipe.domain.subscription.ListScrapping;
import com.nocommittoday.techswipe.domain.subscription.ListScrappingSubscription;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;

public class ListScrappingSubscriptionBuilder extends SubscriptionBuilder {

    @Nullable
    private ListScrapping listScrapping;

    @Nullable
    private ContentScrapping contentScrapping;

    public ListScrappingSubscriptionBuilder() {
    }

    public static ListScrappingSubscription create() {
        return new ListScrappingSubscriptionBuilder().build();
    }

    public ListScrappingSubscriptionBuilder listScrapping(ListScrapping listScrapping) {
        this.listScrapping = listScrapping;
        return this;
    }

    public ListScrappingSubscriptionBuilder contentScrapping(ContentScrapping contentScrapping) {
        this.contentScrapping = contentScrapping;
        return this;
    }

    public ListScrappingSubscription build() {
        fillRequiredFields();
        return Subscription.createListScrapping(
                id,
                providerId,
                listScrapping,
                contentScrapping
        );
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = new SubscriptionId(LocalAutoIncrementIdUtils.nextId());
        }
        if (providerId == null) {
            providerId = new TechContentProviderId(LocalAutoIncrementIdUtils.nextId());
        }
        if (listScrapping == null) {
            listScrapping = ListScrappingBuilder.create();
        }
        if (contentScrapping == null) {
            contentScrapping = ContentScrappingBuilder.create();
        }
    }
}
