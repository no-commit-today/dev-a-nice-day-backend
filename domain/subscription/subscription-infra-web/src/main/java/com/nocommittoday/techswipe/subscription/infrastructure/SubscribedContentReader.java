package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.SubscribedContentResult;
import com.nocommittoday.techswipe.subscription.domain.Subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscribedContentReader {

    boolean supports(Subscription subscription);

    boolean supportsInit(Subscription subscription);

    List<SubscribedContentResult> getList(Subscription subscription, LocalDate date);
}
