package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class TechContentNotFoundException extends AbstractDomainException {

    public TechContentNotFoundException(TechContentId id) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, "id=" + id.value());
    }

    public TechContentNotFoundException(String url) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, "url=" + url);
    }
}
