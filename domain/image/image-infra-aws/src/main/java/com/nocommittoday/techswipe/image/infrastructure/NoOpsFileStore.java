package com.nocommittoday.techswipe.image.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(S3FileStore.class)
class NoOpsFileStore implements FileStore {

    @Override
    public String store(final ImageData imageData, final String storedName) {
        return storedName;
    }
}
