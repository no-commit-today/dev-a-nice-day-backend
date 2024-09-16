package com.nocommittoday.techswipe.domain.image.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class NotSupportedImageException extends AbstractDomainException {

    public NotSupportedImageException(String contentType) {
        super(ImageErrorCode.NOT_SUPPORTED_IMAGE, Map.of("contentType", contentType));
    }
}
