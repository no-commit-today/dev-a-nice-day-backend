package com.nocommittoday.techswipe.batch.service.impl;


import com.nocommittoday.techswipe.batch.service.FileStore;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Profile("!s3")
public class NoOpsFileStore implements FileStore {

    @Override
    public String store(final Resource resource, final String storedName) {
        return storedName;
    }
}
