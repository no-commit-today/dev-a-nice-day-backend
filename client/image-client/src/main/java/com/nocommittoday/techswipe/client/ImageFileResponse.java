package com.nocommittoday.techswipe.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

@Slf4j
public class ImageFileResponse {

    private final File file;

    private final MediaType contentType;

    public ImageFileResponse(final File file, final MediaType contentType) {
        this.file = file;
        this.contentType = contentType;
    }

    public MediaType getContentType() {
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
        } catch (final IOException e) {
            log.warn("파일 삭제 중 실패했습니다. 파일: {}", file, e);
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
