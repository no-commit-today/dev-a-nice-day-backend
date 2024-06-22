package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageReader {

    private final ImageJpaRepository imageRepository;

    public Image get(final Image.Id id) {
        return imageRepository.findById(id.value())
                .filter(ImageEntity::isUsed)
                .map(ImageEntity::toDomain)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    public List<Image> getAll(final Collection<Image.Id> ids) {
        return imageRepository.findAllById(ids.stream().map(Image.Id::value).toList()).stream()
                .filter(ImageEntity::isUsed)
                .map(ImageEntity::toDomain)
                .toList();
    }
}
