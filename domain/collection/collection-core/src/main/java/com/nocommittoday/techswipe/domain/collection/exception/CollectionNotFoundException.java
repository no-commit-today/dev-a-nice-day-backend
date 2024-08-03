package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionNotFoundException extends AbstractDomainException {

    public CollectionNotFoundException(CollectedContentId id) {
        super(CollectionErrorCode.NOT_FOUND, "id=" + id);
    }
}
