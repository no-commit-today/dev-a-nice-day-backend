package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class CollectionNotFoundException extends AbstractDomainException {

    public CollectionNotFoundException(CollectedContentId id) {
        super(CollectionErrorCode.NOT_FOUND, Map.of("id", id));
    }
}
