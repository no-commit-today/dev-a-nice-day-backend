package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionRegisterFailureException;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public record SubscriptionRegister(
        @NonNull TechContentProvider.TechContentProviderId providerId,
        @NonNull SubscriptionType type,
        @Nullable String rssUrl,
        @Nullable String atomUrl,
        @NonNull ContentCrawling contentCrawling,
        @NonNull List<ListCrawling> listCrawlings
) {

    public SubscriptionRegister {
        if (type == SubscriptionType.RSS) {
            if (rssUrl == null) {
                throw new SubscriptionRegisterFailureException("rssUrl이 필요합니다.");
            }
        } else if (type == SubscriptionType.ATOM) {
            if (atomUrl == null) {
                throw new SubscriptionRegisterFailureException("atomUrl이 필요합니다.");
            }
        } else if (type == SubscriptionType.LIST_CRAWLING) {
            if (listCrawlings.isEmpty()) {
                throw new SubscriptionRegisterFailureException("listCrawlings이 필요합니다.");
            }
            if (contentCrawling.title() == null
                    || contentCrawling.date() == null
                    || contentCrawling.content() == null
            ) {
                throw new SubscriptionRegisterFailureException("contentCrawling이 필요합니다.");
            }
        }
    }
}
