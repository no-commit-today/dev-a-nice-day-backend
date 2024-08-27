package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.net.URI;

public class AdminCollectionAlreadyCollectedException extends AbstractDomainException {

    public AdminCollectionAlreadyCollectedException(URI uri) {
        super(AdminErrorCode.COLLECTION_ALREADY_COLLECTED, "uri=" + uri);
    }
}
