package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionIllegalProviderIdException extends AbstractDomainException {

    public CollectionIllegalProviderIdException(TechContentProviderId id) {
        super(CollectionErrorCode.ILLEGAL_PROVIDER_ID, "id=" + id);
    }
}
