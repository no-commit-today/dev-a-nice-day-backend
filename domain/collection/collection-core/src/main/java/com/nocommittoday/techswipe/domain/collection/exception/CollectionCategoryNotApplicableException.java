package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionCategoryNotApplicableException extends AbstractDomainException {

    public CollectionCategoryNotApplicableException(CollectedContentId id) {
        super(CollectionErrorCode.CATEGORY_NOT_APPLICABLE, "id=" + id);
    }
}
