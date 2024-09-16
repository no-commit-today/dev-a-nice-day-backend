package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class TechContentProviderNotFoundException extends AbstractDomainException {

    public TechContentProviderNotFoundException(TechContentProviderId id) {
        super(TechContentErrorCode.PROVIDER_NOT_FOUND, Map.of("id", id));
    }
}
