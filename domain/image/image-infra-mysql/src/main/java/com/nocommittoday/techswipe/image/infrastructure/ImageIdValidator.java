package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.domain.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
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
