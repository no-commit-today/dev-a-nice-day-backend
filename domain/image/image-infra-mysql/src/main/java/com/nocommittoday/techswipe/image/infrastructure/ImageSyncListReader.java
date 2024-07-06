package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageSyncListReader {

    private final ImageJpaRepository imageJpaRepository;

    public List<Image> getList(final LocalDateTime from, final LocalDateTime to) {
        return imageJpaRepository.findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThanAndDeletedIsFalse(
                from, to)
                .stream()
                .map(ImageEntity::toDomain)
                .toList();
    }
}
