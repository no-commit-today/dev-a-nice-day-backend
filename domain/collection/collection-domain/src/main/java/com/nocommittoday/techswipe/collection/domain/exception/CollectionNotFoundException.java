package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionNotFoundException extends AbstractDomainException {

    public CollectionNotFoundException(CollectedContentId id) {
        super(CollectionErrorCode.NOT_FOUND, "id=" + id);
    }
}
