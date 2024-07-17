package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategorizeUnableException extends AbstractDomainException {

    public CollectionCategorizeUnableException(
            final CollectedContent.Id id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.CATEGORIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
