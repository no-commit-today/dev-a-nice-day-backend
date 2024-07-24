package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContentId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategoryNotApplicableException extends AbstractDomainException {

    public CollectionCategoryNotApplicableException(final CollectedContentId id) {
        super(CollectionErrorCode.CATEGORY_NOT_APPLICABLE, "id=" + id);
    }
}
