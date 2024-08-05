package com.nocommittoday.techswipe.storage.mysql.test;

import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import jakarta.annotation.Nullable;

public class ImageEntityBuilder {

    @Nullable
    private Long id;
    @Nullable
    private String url;
    @Nullable
    private String originalUrl;
    @Nullable
    private String storedName;

    public static ImageEntity create() {
        return create(false);
    }

    public static ImageEntity create(boolean withId) {
        return new ImageEntityBuilder().build(withId);
    }

    public ImageEntityBuilder() {
    }

    public ImageEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ImageEntityBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ImageEntityBuilder originalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public ImageEntityBuilder storedName(String storedName) {
        this.storedName = storedName;
        return this;
    }

    public ImageEntity build() {
        return build(false);
    }

    public ImageEntity build(boolean withId) {
        fillRequiredFields(withId);
        return new ImageEntity(
            id,
            url,
            originalUrl,
            storedName
        );
    }



    private void fillRequiredFields(boolean withId) {
        if (withId && id == null) {
            id = LocalAutoIncrementIdUtils.nextId();
        }
        long fieldId = id != null ? id : LocalAutoIncrementIdUtils.nextId();

        if (url == null) {
            url = "image-url-" + fieldId;
        }
        if (originalUrl == null) {
            originalUrl = "image-originalUrl-" + fieldId;
        }
        if (storedName == null) {
            storedName = "image-storedName-" + fieldId;
        }
    }
}
