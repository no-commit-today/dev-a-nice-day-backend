package com.nocommittoday.techswipe.image.storage.mysql;

import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.domain.ImageSave;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class ImageEntityMapper {

    private final ImageJpaRepository imageJpaRepository;

    public ImageEntityMapper(ImageJpaRepository imageJpaRepository) {
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
