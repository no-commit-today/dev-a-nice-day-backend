package com.nocommittoday.techswipe.storage.mysql.image;

import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageSave;
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
