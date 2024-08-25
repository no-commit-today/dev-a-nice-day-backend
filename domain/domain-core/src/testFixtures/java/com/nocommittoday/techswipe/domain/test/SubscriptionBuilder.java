package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;

public abstract class SubscriptionBuilder {

    @Nullable
    protected SubscriptionId id;

    @Nullable
    protected TechContentProviderId providerId;

    protected SubscriptionBuilder() {
    }

    public SubscriptionBuilder id(SubscriptionId id) {
        this.id = id;
        return this;
    }

    public SubscriptionBuilder providerId(TechContentProviderId providerId) {
        this.providerId = providerId;
        return this;
    }
}
