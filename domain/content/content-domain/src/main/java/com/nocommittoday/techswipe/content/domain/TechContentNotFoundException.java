package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class TechContentNotFoundException extends AbstractDomainException {

    public TechContentNotFoundException(final TechContent.Id id) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, "id=" + id.value());
    }
}
