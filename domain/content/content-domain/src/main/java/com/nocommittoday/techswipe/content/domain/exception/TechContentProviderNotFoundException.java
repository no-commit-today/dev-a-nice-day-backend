package com.nocommittoday.techswipe.content.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class TechContentProviderNotFoundException extends AbstractDomainException {

    public TechContentProviderNotFoundException(final TechContentProviderId id) {
        super(TechContentErrorCode.PROVIDER_NOT_FOUND, "id=" + id.value());
    }
}
