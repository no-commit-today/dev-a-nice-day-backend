package com.nocommittoday.techswipe.image.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
class NoOpsFileStoreAdapter implements FileStore {

    @Override
    public String store(
            final Resource resource,
            final String storedName
    ) {
        return "saved-" + storedName + "-" + resource.getFilename();
    }
}
