package com.nocommittoday.techswipe.content.domain.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class TechContentProviderUrlExistsException extends AbstractDomainException {

    public TechContentProviderUrlExistsException(final String url) {
        super(TechContentErrorCode.PROVIDER_URL_EXISTS, "url=" + url);
    }
}
