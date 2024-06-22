package com.nocommittoday.techswipe.image.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class ImageNotFoundException extends AbstractDomainException {

    public ImageNotFoundException(final Image.Id imageId) {
        super(ImageErrorCode.IMAGE_NOT_FOUND, "이미지 ID=" + imageId.value());
    }
}
