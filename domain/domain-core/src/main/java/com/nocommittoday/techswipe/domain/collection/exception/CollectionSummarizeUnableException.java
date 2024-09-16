package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class CollectionSummarizeUnableException extends AbstractDomainException {

    public CollectionSummarizeUnableException(CollectedContentId id, CollectionStatus status) {
        super(CollectionErrorCode.SUMMARIZE_UNABLE, Map.of("id", id, "status", status));
    }
}
