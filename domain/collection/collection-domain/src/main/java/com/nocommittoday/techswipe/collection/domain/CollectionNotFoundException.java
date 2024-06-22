package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionNotFoundException extends AbstractDomainException {

    public CollectionNotFoundException(final CollectedContent.Id id) {
        super(CollectionErrorCode.NOT_FOUND, "id=" + id);
    }
}
