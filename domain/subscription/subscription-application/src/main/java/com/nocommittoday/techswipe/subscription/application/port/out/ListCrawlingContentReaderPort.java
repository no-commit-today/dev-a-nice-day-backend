package com.nocommittoday.techswipe.subscription.application.port.out;

import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawlingSubscription;

import java.time.LocalDate;
import java.util.List;

public interface ListCrawlingContentReaderPort {

    List<SubscribedContent> getList(
            final ListCrawlingSubscription subscription,
            final LocalDate date
    );
}
