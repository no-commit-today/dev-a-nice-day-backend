package com.nocommittoday.techswipe.domain.image;

import com.nocommittoday.techswipe.domain.image.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.image.ImageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ImageIdValidator {

    private final ImageJpaRepository imageJpaRepository;

    public ImageIdValidator(ImageJpaRepository imageJpaRepository) {
        this.imageJpaRepository = imageJpaRepository;
    }

    public void validate(ImageId imageId) {
        if (!imageJpaRepository.existsByIdAndDeletedIsFalse(imageId.value())) {
            throw new ImageNotFoundException(imageId);
        }
    }
}
