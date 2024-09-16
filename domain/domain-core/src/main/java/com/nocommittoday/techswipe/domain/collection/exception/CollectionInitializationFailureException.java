package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class CollectionInitializationFailureException extends AbstractDomainException {

    public CollectionInitializationFailureException(CollectedContentId id) {
        super(CollectionErrorCode.INITIALIZATION_FAILURE, Map.of("id", id));
    }
}
