package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategorizeFailureException extends AbstractDomainException {

    public CollectionCategorizeFailureException(final String message) {
        super(CollectionErrorCode.CATEGORIZE_FAILURE, message);
    }
}
