package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.infrastructure.ImageReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageUrlQueryService {

    private final ImageReader imageReader;

    public ImageUrlResult get(ImageId id) {
        return ImageUrlResult.from(imageReader.get(id));
    }

    public List<ImageUrlResult> getAll(List<ImageId> ids) {
        return imageReader.getAll(ids).stream()
                .map(ImageUrlResult::from)
                .toList();
    }
}
