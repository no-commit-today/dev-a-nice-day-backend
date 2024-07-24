package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategoryNotEditableException extends AbstractDomainException {

    public CollectionCategoryNotEditableException(final CollectedContentId id) {
        super(CollectionErrorCode.CATEGORY_NOT_EDITABLE, "id=" + id);
    }
}
