package com.nocommittoday.techswipe.image.adapter.out.mysql;

import com.nocommittoday.techswipe.image.application.port.out.ImageSave;
import com.nocommittoday.techswipe.image.application.port.out.ImageSavePort;
import com.nocommittoday.techswipe.image.infrastructure.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.infrastructure.mysql.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ImageSaveAdapter implements ImageSavePort {

    private final ImageJpaRepository imageRepository;

    @Override
    public long save(final ImageSave imageSave) {
        return imageRepository.save(new ImageEntity(
                null,
                imageSave.url(),
                imageSave.originalUrl(),
                imageSave.storedName()
        )).getId();
    }
}
