package com.nocommittoday.techswipe.infrastructure.image;

import com.nocommittoday.techswipe.domain.image.ImageFile;
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
