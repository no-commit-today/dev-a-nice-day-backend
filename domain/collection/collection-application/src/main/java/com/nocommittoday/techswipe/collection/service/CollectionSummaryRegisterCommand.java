package com.nocommittoday.techswipe.collection.service;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import lombok.NonNull;

public record CollectionSummaryRegisterCommand(
        @NonNull CollectedContent.Id id,
        @NonNull String summary
) {
}
