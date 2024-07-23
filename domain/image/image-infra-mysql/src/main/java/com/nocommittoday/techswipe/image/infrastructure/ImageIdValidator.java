package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ImageIdValidator {

    private final ImageJpaRepository imageJpaRepository;

    public void validate(final Image.Id imageId) {
        if (!imageJpaRepository.existsByIdAndDeletedIsFalse(imageId.value())) {
            throw new ImageNotFoundException(imageId);
        }
    }
}
