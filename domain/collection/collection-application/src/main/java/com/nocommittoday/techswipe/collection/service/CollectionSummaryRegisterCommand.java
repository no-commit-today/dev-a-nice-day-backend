package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public record CollectionSummaryRegisterCommand(
        CollectedContentId id,
        String summary
) {
}
