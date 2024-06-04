package com.nocommittoday.techswipe.subscription.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

import java.time.LocalDate;
import java.util.List;

public interface SubscribedContentListQuery {

    List<SubscribedContentResult> getList(
            final TechContentProvider.TechContentProviderId providerId,
            final LocalDate date
    );
}
