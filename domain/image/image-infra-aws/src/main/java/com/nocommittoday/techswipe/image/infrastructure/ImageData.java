package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageContentType;
import lombok.NonNull;

import java.io.File;

public record ImageData(
        @NonNull ImageContentType contentType,
        @NonNull File file
) {
}
