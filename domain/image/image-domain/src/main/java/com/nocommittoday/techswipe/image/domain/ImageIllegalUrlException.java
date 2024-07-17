package com.nocommittoday.techswipe.image.domain;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class ImageIllegalUrlException extends AbstractDomainException {

    public ImageIllegalUrlException(final String url) {
        super(ImageErrorCode.IMAGE_ILLEGAL_URL, "url=" + url);
    }

}
