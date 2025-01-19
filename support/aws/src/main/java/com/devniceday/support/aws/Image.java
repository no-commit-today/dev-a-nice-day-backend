package com.devniceday.support.aws;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public interface Image {

    String ext();

    ImageContentType contentType();

    long contentLength() throws IOException;

    InputStream inputStream() throws IOException;

    static Image of(Resource resource, ImageContentType contentType) {
        return new ResourceImage(resource, contentType);
    }
}
