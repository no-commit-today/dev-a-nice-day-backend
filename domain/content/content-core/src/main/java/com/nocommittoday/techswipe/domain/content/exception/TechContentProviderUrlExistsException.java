package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class TechContentProviderUrlExistsException extends AbstractDomainException {

    public TechContentProviderUrlExistsException(String url) {
        super(TechContentErrorCode.PROVIDER_URL_EXISTS, "url=" + url);
    }
}
