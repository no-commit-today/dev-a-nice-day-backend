package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class TechContentProviderNotFoundException extends AbstractDomainException {

    public TechContentProviderNotFoundException(final TechContentProviderId id) {
        super(TechContentErrorCode.PROVIDER_NOT_FOUND, "id=" + id.value());
    }
}
