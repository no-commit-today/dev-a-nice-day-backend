package com.nocommittoday.techswipe.image.application.port.in;

public interface ImageStoreUseCase {

    long store(String originUrl, String dirToStore);
}
