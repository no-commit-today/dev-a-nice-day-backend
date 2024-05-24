package com.nocommittoday.techswipe.image.application.port.in;

import com.nocommittoday.techswipe.image.domain.Image;

import java.util.List;

public interface ImageUrlQuery {

    ImageUrlResult get(Image.ImageId id);

    List<ImageUrlResult> getAll(List<Image.ImageId> ids);
}
