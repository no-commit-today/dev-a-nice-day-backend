package com.nocommittoday.techswipe.image.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;

@Profile("local")
@Component
class NoOpsFileStoreAdapter implements FileStore {

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
