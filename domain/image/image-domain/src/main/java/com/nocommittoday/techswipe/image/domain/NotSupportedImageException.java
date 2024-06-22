package com.nocommittoday.techswipe.image.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class NotSupportedImageException extends AbstractDomainException {

    public NotSupportedImageException(final String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "Content type=" + contentType);
    }
}
