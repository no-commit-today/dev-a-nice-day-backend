package com.nocommittoday.techswipe.image.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Component
@ConditionalOnMissingBean(S3FileStore.class)
class NoOpsFileStore implements FileStore {

    @Override
    public String store(
            final Resource resource,
            final String storedName
    ) {
        try {
            return resource.getURL().toString();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
