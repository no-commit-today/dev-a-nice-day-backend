package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionCategorizeUnableException extends AbstractDomainException {

    public CollectionCategorizeUnableException(
            final CollectedContentId id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.CATEGORIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
