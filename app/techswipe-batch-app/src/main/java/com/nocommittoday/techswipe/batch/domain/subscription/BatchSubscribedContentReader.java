package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;

import java.util.List;

public interface BatchSubscribedContentReader {

    boolean supports(Subscription subscription);

    List<SubscribedContent> getList(Subscription subscription, int page);
}
