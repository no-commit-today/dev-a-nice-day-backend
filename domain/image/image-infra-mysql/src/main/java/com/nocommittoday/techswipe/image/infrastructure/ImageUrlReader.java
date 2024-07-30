package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
@RequiredArgsConstructor
public class ImageUrlReader {

    private final ImageReader imageReader;

    public String get(ImageId id) {
        return imageReader.get(id).getUrl();
    }

    public String getOrNull(@Nullable ImageId id) {
        return id == null ? null : get(id);
    }
}
