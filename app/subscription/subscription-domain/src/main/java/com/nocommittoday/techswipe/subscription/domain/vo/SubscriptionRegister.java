package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public record SubscriptionRegister(
        @NonNull SubscriptionType type,
        @Nullable String rssUrl,
        @Nullable String atomUrl,
        @Nullable List<ListCrawling> listCrawlings
) {

}
