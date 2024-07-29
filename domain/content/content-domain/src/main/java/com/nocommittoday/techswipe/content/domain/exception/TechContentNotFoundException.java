package com.nocommittoday.techswipe.content.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class TechContentNotFoundException extends AbstractDomainException {

    public TechContentNotFoundException(final TechContentId id) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, "id=" + id.value());
    }

    public TechContentNotFoundException(final String url) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, "url=" + url);
    }
}
