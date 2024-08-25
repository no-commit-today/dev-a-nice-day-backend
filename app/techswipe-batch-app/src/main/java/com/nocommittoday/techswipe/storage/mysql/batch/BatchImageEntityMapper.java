package com.nocommittoday.techswipe.storage.mysql.batch;

import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageSave;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageJpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class BatchImageEntityMapper {

    private final ImageJpaRepository imageJpaRepository;

    public BatchImageEntityMapper(ImageJpaRepository imageJpaRepository) {
        this.imageJpaRepository = imageJpaRepository;
    }

    @Nullable
    public ImageEntity from(@Nullable ImageId id) {
        return id == null ? null : imageJpaRepository.getReferenceById(id.value());
    }

    public ImageEntity from(ImageSave imageSave) {
        return new ImageEntity(
                null,
                imageSave.url(),
                imageSave.originalUrl(),
                imageSave.storedName()
        );
    }
}
