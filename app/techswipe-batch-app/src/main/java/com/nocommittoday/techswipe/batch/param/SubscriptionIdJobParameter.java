package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;
import org.springframework.beans.factory.annotation.Value;

public class SubscriptionIdJobParameter {

    public static final String NAME = "SubscriptionIdJobParameter";

    private SubscriptionId id;

    @Value("#{jobParameters['subscription.id']}")
    public void setId(Long id) {
        this.id = new SubscriptionId(id);
    }

    public SubscriptionId getId() {
        return id;
    }
}
