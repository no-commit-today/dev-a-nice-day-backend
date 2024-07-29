package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategorizeUnableException extends AbstractDomainException {

    public CollectionCategorizeUnableException(
            final CollectedContentId id,
            final CollectionStatus status
    ) {
        super(CollectionErrorCode.CATEGORIZE_UNABLE, "id=" + id + ", status=" + status);
    }
}
