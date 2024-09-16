package com.nocommittoday.techswipe.domain.content.exception;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class TechContentNotFoundException extends AbstractDomainException {

    public TechContentNotFoundException(TechContentId id) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, Map.of("id", id));
    }

    public TechContentNotFoundException(String url) {
        super(TechContentErrorCode.CONTENT_NOT_FOUND, "url=" + url);
    }
}
