package com.nocommittoday.techswipe.image.application.port.out;

import com.nocommittoday.techswipe.image.domain.Image;

import java.util.List;

public interface ImageQueryPort {

    Image get(Image.ImageId id);

    List<Image> getAll(List<Image.ImageId> ids);
}
