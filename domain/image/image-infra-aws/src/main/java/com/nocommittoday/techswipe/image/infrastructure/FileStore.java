package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageFile;

public interface FileStore {

    String store(final ImageFile imageFile, final String storedName);
}
