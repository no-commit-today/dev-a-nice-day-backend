package com.devniceday.batch.domain;

import java.util.List;

public interface SubscribedContentFetchCommand {

    boolean supports(Subscription subscription);

    List<SubscribedContent> fetch(Subscription subscription, int page);
}
