package com.nocommittoday.techswipe.domain.image.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class ImageIllegalUrlException extends AbstractDomainException {

    public ImageIllegalUrlException(String url) {
        super(ImageErrorCode.IMAGE_ILLEGAL_URL, "url=" + url);
    }

}