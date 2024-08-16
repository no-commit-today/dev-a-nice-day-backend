package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class AdminCollectionCategoryEditFailureException extends AbstractDomainException {

    public AdminCollectionCategoryEditFailureException(CollectionStatus status) {
        super(AdminErrorCode.COLLECTION_CATEGORY_EDIT_FAILURE, "status=" + status);
    }
}
