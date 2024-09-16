package com.nocommittoday.techswipe.domain.image.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.image.ImageId;

import java.util.Map;

public class ImageNotFoundException extends AbstractDomainException {

    public ImageNotFoundException(ImageId imageId) {
        super(ImageErrorCode.IMAGE_NOT_FOUND, Map.of("id", imageId));
    }
}
