package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;
import com.nocommittoday.techswipe.image.domain.Image;

public class ImageNotFoundException extends AbstractDomainException {

    public ImageNotFoundException(final Image.Id imageId) {
        super(ImageErrorCode.IMAGE_NOT_FOUND, "이미지 ID=" + imageId.value());
    }
}
