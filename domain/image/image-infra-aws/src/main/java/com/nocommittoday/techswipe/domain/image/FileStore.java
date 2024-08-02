package com.nocommittoday.techswipe.domain.image;

public interface FileStore {

    String store(ImageFile imageFile, String storedName);
}
