package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.image.domain.Image;

public record ImageUrlResult(
        Image.Id id,
        String url
) {

    public static ImageUrlResult from(Image image) {
        return new ImageUrlResult(image.getId(), image.getUrl());
    }
}
