package com.nocommittoday.techswipe.domain.image.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class NotSupportedImageException extends AbstractDomainException {

    public NotSupportedImageException(String url, String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "url=" + url + ", contentType=" + contentType);
    }

    public NotSupportedImageException(String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "contentType=" + contentType);
    }
}
