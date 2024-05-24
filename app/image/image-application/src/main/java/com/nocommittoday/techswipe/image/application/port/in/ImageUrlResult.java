package com.nocommittoday.techswipe.image.application.port.in;

import com.nocommittoday.techswipe.image.domain.Image;

public record ImageUrlResult(
        Image.ImageId id,
        String url
) {

    public static ImageUrlResult from(Image image) {
        return new ImageUrlResult(image.getId(), image.getUrl());
    }
}
