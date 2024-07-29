package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;
import com.nocommittoday.techswipe.image.domain.ImageId;

public class ImageNotFoundException extends AbstractDomainException {

    public ImageNotFoundException(final ImageId imageId) {
        super(ImageErrorCode.IMAGE_NOT_FOUND, "이미지 ID=" + imageId.value());
    }
}
