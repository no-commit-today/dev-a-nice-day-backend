package com.nocommittoday.techswipe.subscription.application.port.in;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;

import java.util.List;

public interface SubscribedContentAllListQuery {

    List<SubscribedContentResult> getAllList(
            final TechContentProvider.TechContentProviderId providerId
    );
}
