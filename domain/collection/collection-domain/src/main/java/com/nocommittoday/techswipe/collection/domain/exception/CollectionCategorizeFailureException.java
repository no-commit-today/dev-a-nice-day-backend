package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionCategorizeFailureException extends AbstractDomainException {

    public CollectionCategorizeFailureException(final String message) {
        super(CollectionErrorCode.CATEGORIZE_FAILURE, message);
    }
}
