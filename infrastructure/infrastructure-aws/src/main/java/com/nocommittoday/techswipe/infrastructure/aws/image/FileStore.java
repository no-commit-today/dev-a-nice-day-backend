package com.nocommittoday.techswipe.infrastructure.aws.image;

import com.nocommittoday.techswipe.domain.image.ImageFile;

public interface FileStore {

    String store(ImageFile imageFile, String storedName);
}
