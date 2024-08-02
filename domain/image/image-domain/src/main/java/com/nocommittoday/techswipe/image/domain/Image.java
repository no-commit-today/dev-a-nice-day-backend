package com.nocommittoday.techswipe.image.domain;

public class Image {

    private final ImageId id;

    private final String url;

    private final String originalUrl;

    private final String storedName;

    public Image(
            ImageId id,
            String url,
            String originalUrl,
            String storedName
    ) {
        this.id = id;
        this.url = url;
        this.originalUrl = originalUrl;
        this.storedName = storedName;
    }

    public ImageId getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getStoredName() {
        return storedName;
    }

    public String getUrl() {
        return url;
    }
}
