package com.nocommittoday.techswipe.subscription.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;

import java.time.LocalDate;
import java.util.List;

public interface SubscribedContentListQuery {

    List<SubscribedContent> getList(
            final TechContentProvider.TechContentProviderId providerId,
            final LocalDate date
    );
}
