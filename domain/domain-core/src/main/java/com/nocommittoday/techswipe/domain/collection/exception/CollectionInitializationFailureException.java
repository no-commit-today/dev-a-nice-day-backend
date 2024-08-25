package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionInitializationFailureException extends AbstractDomainException {

    public CollectionInitializationFailureException(CollectedContentId id) {
        super(CollectionErrorCode.INITIALIZATION_FAILURE, "id=" + id);
    }
}
