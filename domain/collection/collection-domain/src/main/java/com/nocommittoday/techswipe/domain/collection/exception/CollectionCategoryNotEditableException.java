package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategoryNotEditableException extends AbstractDomainException {

    public CollectionCategoryNotEditableException(CollectedContentId id) {
        super(CollectionErrorCode.CATEGORY_NOT_EDITABLE, "id=" + id);
    }
}
