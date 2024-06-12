package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.infrastructure.ImageReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageUrlQueryService {

    private final ImageReader imageReader;

    public ImageUrlResult get(final Image.ImageId id) {
        return ImageUrlResult.from(imageReader.get(id));
    }

    public List<ImageUrlResult> getAll(final List<Image.ImageId> ids) {
        return imageReader.getAll(ids).stream()
                .map(ImageUrlResult::from)
                .toList();
    }
}
