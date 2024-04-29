package com.nocommittoday.techswipe.batch.service;

import org.springframework.core.io.Resource;

public interface FileStore {

    String store(Resource resource, String storedName);
}
