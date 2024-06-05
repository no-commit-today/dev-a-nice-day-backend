package com.nocommittoday.techswipe.image.application.port.in;

import com.nocommittoday.techswipe.image.domain.Image;

public interface ImageStoreUseCase {

    Image.ImageId store(String originUrl, String dirToStore);
}
