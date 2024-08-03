package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionSummarizeUnableException extends AbstractDomainException {

    public CollectionSummarizeUnableException(
            final CollectedContentId id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.SUMMARIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
