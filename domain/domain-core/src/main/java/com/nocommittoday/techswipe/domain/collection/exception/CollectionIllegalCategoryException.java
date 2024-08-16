package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Collection;

public class CollectionIllegalCategoryException extends AbstractDomainException {

    public CollectionIllegalCategoryException(Collection<CollectionCategory> categories) {
        super(CollectionErrorCode.ILLEGAL_CATEGORY, "categories=" + categories);
    }


}
