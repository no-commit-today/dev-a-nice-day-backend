package com.nocommittoday.techswipe.subscription.application.port.out;

import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.vo.AtomSubscription;

import java.time.LocalDate;
import java.util.List;

public interface AtomContentReaderPort {

    List<SubscribedContent> getList(
            final AtomSubscription subscription, final LocalDate date
    );
}
