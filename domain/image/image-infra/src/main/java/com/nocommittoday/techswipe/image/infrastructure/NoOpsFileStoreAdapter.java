package com.nocommittoday.techswipe.image.infrastructure;

import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
class NoOpsFileStoreAdapter implements FileStore {

    @Override
    public String store(
            final @NonNull Resource resource,
            final @NonNull String storedName
    ) {
        return "saved-" + storedName + "-" + resource.getFilename();
    }
}
