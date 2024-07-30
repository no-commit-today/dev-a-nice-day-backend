package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class NotSupportedImageException extends AbstractDomainException {

    public NotSupportedImageException(String url, String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "url=" + url + ", contentType=" + contentType);
    }

    public NotSupportedImageException(String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, "contentType=" + contentType);
    }
}
