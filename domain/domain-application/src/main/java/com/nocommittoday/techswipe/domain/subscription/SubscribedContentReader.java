package com.nocommittoday.techswipe.domain.subscription;

import java.time.LocalDate;
import java.util.List;

public interface SubscribedContentReader {

    boolean supports(Subscription subscription);

    boolean supportsInit(Subscription subscription);

    List<SubscribedContent> getList(Subscription subscription, LocalDate date);
}
