package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.domain.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class ImageReader {

    private final ImageJpaRepository imageRepository;

    public ImageReader(ImageJpaRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image get(ImageId id) {
        return imageRepository.findById(id.value())
                .filter(ImageEntity::isUsed)
                .map(ImageEntity::toDomain)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    public List<Image> getAll(Collection<ImageId> ids) {
        return imageRepository.findAllById(ids.stream().map(ImageId::value).toList()).stream()
                .filter(ImageEntity::isUsed)
                .map(ImageEntity::toDomain)
                .toList();
    }
}
