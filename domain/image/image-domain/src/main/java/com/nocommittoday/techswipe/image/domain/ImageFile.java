package com.nocommittoday.techswipe.image.domain;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

@Slf4j
public class ImageFile {

    private final File file;

    private final ImageContentType contentType;

    public ImageFile(final File file, final ImageContentType contentType) {
        this.file = file;
        this.contentType = contentType;
    }

    public ImageContentType getContentType() {
        return contentType;
    }

    public long getContentLength() {
        return file.length();
    }

    public InputStream getInputStream() {
        try {
            return new FileInputStream(file);
        } catch (final FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void delete() {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    public String toString() {
        return "ImageFileResponse{" +
                "contentType=" + getContentType() +
                ", contentLength=" + getContentLength() +
                '}';
    }
}
