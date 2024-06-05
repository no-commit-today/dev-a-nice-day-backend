package com.nocommittoday.techswipe.image.application.port.out;

import lombok.NonNull;
import org.springframework.core.io.Resource;

public interface FileStorePort {

    String store(@NonNull final Resource resource, @NonNull final String storedName);
}
