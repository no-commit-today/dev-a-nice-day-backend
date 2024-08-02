package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;

public record CollectionSummaryRegisterCommand(
        CollectedContentId id,
        String summary
) {
}
