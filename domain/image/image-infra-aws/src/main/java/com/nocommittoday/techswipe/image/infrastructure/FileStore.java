package com.nocommittoday.techswipe.image.infrastructure;

public interface FileStore {

    String store(final ImageData imageData, final String storedName);
}
