package com.nocommittoday.techswipe.domain.image;

import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class ImageUrlReader {

    private final ImageReader imageReader;

    public ImageUrlReader(ImageReader imageReader) {
        this.imageReader = imageReader;
    }

    public String get(ImageId id) {
        return imageReader.get(id).getUrl();
    }

    public String getOrNull(@Nullable ImageId id) {
        return id == null ? null : get(id);
    }
}