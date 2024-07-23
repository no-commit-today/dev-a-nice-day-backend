package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class CollectionAlreadyCollectedException extends AbstractDomainException {

    public CollectionAlreadyCollectedException(final String url) {
        super(CollectionErrorCode.ILLEGAL_PROVIDER_ID, "url=" + url);
    }
}
