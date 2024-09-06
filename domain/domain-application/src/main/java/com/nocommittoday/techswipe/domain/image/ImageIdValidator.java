package com.nocommittoday.techswipe.domain.image;

import com.nocommittoday.techswipe.domain.core.DomainValidationException;
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
            throw new DomainValidationException("잘못된 이미지 ID 입니다.", imageId.value());
        }
    }
}
