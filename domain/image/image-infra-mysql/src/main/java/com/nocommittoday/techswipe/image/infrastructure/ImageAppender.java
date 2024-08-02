package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageSave;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
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
