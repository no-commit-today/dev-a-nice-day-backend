package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class CollectionPublishUnableException extends AbstractDomainException {

    public CollectionPublishUnableException(CollectedContentId id, CollectionStatus status) {
        super(CollectionErrorCode.PUBLISH_UNABLE, Map.of("id", id, "status", status));
    }
}
