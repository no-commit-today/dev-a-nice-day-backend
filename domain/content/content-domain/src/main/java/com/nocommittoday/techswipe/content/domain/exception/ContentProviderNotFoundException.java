package com.nocommittoday.techswipe.content.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;
import lombok.NonNull;

public class ContentProviderNotFoundException extends AbstractDomainException {

    public ContentProviderNotFoundException(@NonNull final TechContentProvider.TechContentProviderId id) {
        super(ContentErrorCode.PROVIDER_NOT_FOUND, "id=" + id.value());
    }
}