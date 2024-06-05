package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class NotSupportedImageException extends AbstractDomainException {

    public NotSupportedImageException(final String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "Content type=" + contentType);
    }
}
