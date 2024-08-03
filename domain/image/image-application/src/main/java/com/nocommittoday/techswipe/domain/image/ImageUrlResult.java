package com.nocommittoday.techswipe.domain.image;

public record ImageUrlResult(
        ImageId id,
        String url
) {

    public static ImageUrlResult from(Image image) {
        return new ImageUrlResult(image.getId(), image.getUrl());
    }
}
