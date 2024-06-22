package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;
import lombok.NonNull;

public class TechContentProviderNotFoundException extends AbstractDomainException {

    public TechContentProviderNotFoundException(@NonNull final TechContentProvider.Id id) {
        super(TechContentErrorCode.PROVIDER_NOT_FOUND, "id=" + id.value());
    }
}
