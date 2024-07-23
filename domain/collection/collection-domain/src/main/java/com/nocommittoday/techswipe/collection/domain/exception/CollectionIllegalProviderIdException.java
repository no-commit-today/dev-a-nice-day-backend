package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionIllegalProviderIdException extends AbstractDomainException {

    public CollectionIllegalProviderIdException(final TechContentProvider.Id id) {
        super(CollectionErrorCode.ILLEGAL_PROVIDER_ID, "id=" + id);
    }
}
