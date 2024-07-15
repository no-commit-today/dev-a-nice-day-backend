package com.nocommittoday.techswipe.collection.domain;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategoryNotEditableException extends AbstractDomainException {

    public CollectionCategoryNotEditableException(final CollectedContent.Id id) {
        super(CollectionErrorCode.CATEGORY_NOT_EDITABLE, "id=" + id);
    }
}
