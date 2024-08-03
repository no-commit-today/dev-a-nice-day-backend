package com.nocommittoday.techswipe.domain.collection.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class CollectionAlreadyCollectedException extends AbstractDomainException {

    public CollectionAlreadyCollectedException(String url) {
        super(CollectionErrorCode.ILLEGAL_PROVIDER_ID, "url=" + url);
    }
}
