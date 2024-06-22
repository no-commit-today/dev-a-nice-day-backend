package com.nocommittoday.techswipe.image.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Image {

    @NonNull
    private final Image.Id id;

    @NonNull
    private final String url;

    @NonNull
    private final String originalUrl;

    @NonNull
    private final String storedName;

    public record Id(long value) {}
}
