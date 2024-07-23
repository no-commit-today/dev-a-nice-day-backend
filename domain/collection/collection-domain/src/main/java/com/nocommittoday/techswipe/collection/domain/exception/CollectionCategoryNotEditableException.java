package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategoryNotEditableException extends AbstractDomainException {

    public CollectionCategoryNotEditableException(final CollectedContent.Id id) {
        super(CollectionErrorCode.CATEGORY_NOT_EDITABLE, "id=" + id);
    }
}
