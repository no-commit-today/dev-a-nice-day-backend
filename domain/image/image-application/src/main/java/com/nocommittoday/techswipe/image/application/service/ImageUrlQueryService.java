package com.nocommittoday.techswipe.image.application.service;

import com.nocommittoday.techswipe.image.application.port.in.ImageUrlQuery;
import com.nocommittoday.techswipe.image.application.port.in.ImageUrlResult;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.infrastructure.ImageReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ImageUrlQueryService implements ImageUrlQuery {

    private final ImageReader imageReader;

    @Override
    public ImageUrlResult get(final Image.ImageId id) {
        return ImageUrlResult.from(imageReader.get(id));
    }

    @Override
    public List<ImageUrlResult> getAll(final List<Image.ImageId> ids) {
        return imageReader.getAll(ids).stream()
                .map(ImageUrlResult::from)
                .toList();
    }
}
