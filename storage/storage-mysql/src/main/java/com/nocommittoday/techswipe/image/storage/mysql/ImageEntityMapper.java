package com.nocommittoday.techswipe.image.storage.mysql;

import com.nocommittoday.techswipe.image.domain.ImageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class ImageEntityMapper {

    private final ImageJpaRepository imageJpaRepository;

    @Nullable
    public ImageEntity from(@Nullable final ImageId id) {
        return id == null ? null : imageJpaRepository.getReferenceById(id.value());
    }
}
