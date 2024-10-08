package com.nocommittoday.techswipe.infrastructure.aws.image;

import com.nocommittoday.techswipe.domain.image.ImageSave;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ImageAppender {

    private final ImageJpaRepository imageRepository;

    public ImageAppender(ImageJpaRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public long save(ImageSave imageSave) {
        return imageRepository.save(new ImageEntity(
                null,
                imageSave.url(),
                imageSave.originalUrl(),
                imageSave.storedName()
        )).getId();
    }
}
