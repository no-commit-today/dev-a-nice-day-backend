package com.nocommittoday.techswipe.image.adapter.out.mysql;

import com.nocommittoday.techswipe.image.application.port.out.ImageQueryPort;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.image.infrastructure.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.infrastructure.mysql.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
class ImageQueryAdapter implements ImageQueryPort {

    private final ImageJpaRepository imageRepository;

    @Override
    public Image get(final Image.ImageId id) {
        return imageRepository.findById(id.value())
                .map(ImageEntity::toDomain)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    @Override
    public List<Image> getAll(final List<Image.ImageId> ids) {
        return imageRepository.findAllById(ids.stream().map(Image.ImageId::value).toList())
                .stream()
                .map(ImageEntity::toDomain)
                .toList();
    }
}
