package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class CollectionNotFoundException extends AbstractDomainException {

    public CollectionNotFoundException(final CollectedContent.CollectedContentId id) {
        super(CollectionErrorCode.NOT_FOUND, "id=" + id);
    }
}
