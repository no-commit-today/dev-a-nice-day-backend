package com.nocommittoday.techswipe.domain.collection;

public record CollectionSummaryRegisterCommand(
        CollectedContentId id,
        String summary
) {
}
