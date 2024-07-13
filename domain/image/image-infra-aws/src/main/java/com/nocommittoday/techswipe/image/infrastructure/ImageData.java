package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageContentType;
import lombok.NonNull;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ImageData {

    private final long contentLength;
    private final ImageContentType contentType;
    private final Resource resource;

    public ImageData(
            final long contentLength,
            @NonNull final ImageContentType contentType,
            @NonNull final Resource resource
    ) {
        this.contentLength = contentLength;
        this.contentType = contentType;
        this.resource = resource;
    }

    public long contentLength() {
        return contentLength;
    }

    public ImageContentType contentType() {
        return contentType;
    }

    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }
}
