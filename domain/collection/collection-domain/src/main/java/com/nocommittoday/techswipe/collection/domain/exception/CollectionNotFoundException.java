package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionNotFoundException extends AbstractDomainException {

    public CollectionNotFoundException(final CollectedContent.Id id) {
        super(CollectionErrorCode.NOT_FOUND, "id=" + id);
    }
}
