package com.nocommittoday.techswipe.subscription.application.port.out;

import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.vo.RssSubscription;

import java.time.LocalDate;
import java.util.List;

public interface RssContentReaderPort {

    List<SubscribedContent> getList(
            final RssSubscription subscription,
            final LocalDate date
    );
}
