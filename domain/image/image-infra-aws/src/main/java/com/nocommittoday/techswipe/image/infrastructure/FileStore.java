package com.nocommittoday.techswipe.image.infrastructure;

import org.springframework.core.io.Resource;

public interface FileStore {

    String store(final Resource resource, final String storedName);
}
