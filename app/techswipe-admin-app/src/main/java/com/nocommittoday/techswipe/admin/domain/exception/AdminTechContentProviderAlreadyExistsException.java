package com.nocommittoday.techswipe.admin.domain.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.net.URI;

public class AdminTechContentProviderAlreadyExistsException extends AbstractDomainException {

    public AdminTechContentProviderAlreadyExistsException(URI uri) {
        super(AdminErrorCode.TECH_CONTENT_PROVIDER_ALREADY_EXISTS, "uri=" + uri);
    }
}
