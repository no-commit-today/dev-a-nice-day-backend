package com.nocommittoday.techswipe.image.infrastructure;

import lombok.NonNull;
import org.springframework.core.io.Resource;

public interface FileStore {

    String store(@NonNull final Resource resource, @NonNull final String storedName);
}
