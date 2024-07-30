package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageFile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(S3FileStore.class)
class NoOpsFileStore implements FileStore {

    @Override
    public String store(ImageFile imageFile, String storedName) {
        return storedName;
    }
}
