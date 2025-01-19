package com.devniceday.support.aws;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

class ResourceImage implements Image {

    private final Resource resource;

    private final ImageContentType contentType;

    ResourceImage(Resource resource, ImageContentType contentType) {
        this.resource = resource;
        this.contentType = contentType;
    }

    @Override
    public String ext() {
        return contentType().ext();
    }

    @Override
    public ImageContentType contentType() {
        return contentType;
    }

    @Override
    public long contentLength() throws IOException {
        return resource.contentLength();
    }

    @Override
    public InputStream inputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public String toString() {
        return "ResourceImage{" +
                "resource=" + resource +
                '}';
    }
}
