package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionPublishUnableException extends AbstractDomainException {

    public CollectionPublishUnableException(
            final CollectedContent.Id id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.PUBLISH_UNABLE, "id=" + id + ", status=" + status);
    }
}
