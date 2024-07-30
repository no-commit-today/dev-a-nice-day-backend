package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionIllegalProviderIdException extends AbstractDomainException {

    public CollectionIllegalProviderIdException(TechContentProviderId id) {
        super(CollectionErrorCode.ILLEGAL_PROVIDER_ID, "id=" + id);
    }
}
