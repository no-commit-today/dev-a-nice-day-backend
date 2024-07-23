package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionCategoryNotApplicableException extends AbstractDomainException {

    public CollectionCategoryNotApplicableException(final CollectedContent.Id id) {
        super(CollectionErrorCode.CATEGORY_NOT_APPLICABLE, "id=" + id);
    }
}
