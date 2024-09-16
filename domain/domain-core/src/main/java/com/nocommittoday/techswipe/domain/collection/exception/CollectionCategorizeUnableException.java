package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class CollectionCategorizeUnableException extends AbstractDomainException {

    public CollectionCategorizeUnableException(CollectedContentId id, CollectionStatus status) {
        super(CollectionErrorCode.CATEGORIZE_UNABLE, Map.of("id", id, "status", status));
    }
}
