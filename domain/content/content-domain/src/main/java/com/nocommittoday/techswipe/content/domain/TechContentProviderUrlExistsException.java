package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class TechContentProviderUrlExistsException extends AbstractDomainException {

    public TechContentProviderUrlExistsException(final String url) {
        super(TechContentErrorCode.PROVIDER_URL_EXISTS, "url=" + url);
    }
}
