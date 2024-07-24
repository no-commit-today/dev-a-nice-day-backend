package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionSummarizeUnableException extends AbstractDomainException {

    public CollectionSummarizeUnableException(
            final CollectedContentId id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.SUMMARIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
