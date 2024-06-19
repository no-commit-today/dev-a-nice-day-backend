package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionCategorizeUnableException extends AbstractDomainException {

    public CollectionCategorizeUnableException(
            final CollectedContent.CollectedContentId id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.CATEGORIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
