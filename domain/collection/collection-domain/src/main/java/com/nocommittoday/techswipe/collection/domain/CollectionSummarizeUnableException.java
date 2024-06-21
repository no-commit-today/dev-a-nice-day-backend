package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionSummarizeUnableException extends AbstractDomainException {

    public CollectionSummarizeUnableException(
            final CollectedContent.CollectedContentId id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.SUMMARIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
