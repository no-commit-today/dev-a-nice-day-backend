package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.service.exception.ImageApplicationException;

import javax.annotation.Nullable;

public class ImageStoreResult {

    private final boolean success;

    @Nullable
    private final ImageId imageId;

    @Nullable
    private final ImageApplicationException exception;

    private ImageStoreResult(
            final boolean success,
            @Nullable final ImageId imageId,
            @Nullable final ImageApplicationException exception
    ) {
        this.success = success;
        this.imageId = imageId;
        this.exception = exception;
    }

    public static ImageStoreResult success(final ImageId imageId) {
        return new ImageStoreResult(true, imageId, null);
    }

    public static ImageStoreResult fail(final Exception exception) {
        return new ImageStoreResult(false, null, new ImageApplicationException(exception));
    }

    public boolean isSuccess() {
        return success;
    }

    public ImageId get() {
        if (!success) {
            throw exception;
        }
        return imageId;
    }


}
