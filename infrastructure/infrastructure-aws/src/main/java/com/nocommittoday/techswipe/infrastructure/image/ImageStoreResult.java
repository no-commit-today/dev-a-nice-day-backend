package com.nocommittoday.techswipe.infrastructure.image;

import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.exception.ImageApplicationException;

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

    public static ImageStoreResult success(ImageId imageId) {
        return new ImageStoreResult(true, imageId, null);
    }

    public static ImageStoreResult fail(Exception exception) {
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
