package com.nocommittoday.techswipe.domain.image.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;
import com.nocommittoday.techswipe.domain.image.ImageId;

public class ImageNotFoundException extends AbstractDomainException {

    public ImageNotFoundException(ImageId imageId) {
        super(ImageErrorCode.IMAGE_NOT_FOUND, "이미지 ID=" + imageId.value());
    }
}
