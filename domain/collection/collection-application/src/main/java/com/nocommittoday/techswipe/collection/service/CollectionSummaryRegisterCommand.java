package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import lombok.NonNull;

public record CollectionSummaryRegisterCommand(
        @NonNull CollectedContentId id,
        @NonNull String summary
) {
}
