package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class NotSupportedImageException extends AbstractDomainException {

    public NotSupportedImageException(final String url, final String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "url=" + url + ", contentType=" + contentType);
    }

    public NotSupportedImageException(final String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "contentType=" + contentType);
    }
}