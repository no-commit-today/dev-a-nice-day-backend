package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionSummarizeUnableException extends AbstractDomainException {

    public CollectionSummarizeUnableException(
            final CollectedContent.Id id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.SUMMARIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
