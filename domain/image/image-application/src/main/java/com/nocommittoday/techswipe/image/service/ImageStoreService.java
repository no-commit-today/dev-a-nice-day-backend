package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.core.infrastructure.UuidHolder;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageSave;
import com.nocommittoday.techswipe.image.infrastructure.FileStore;
import com.nocommittoday.techswipe.image.infrastructure.ImageAppender;
import com.nocommittoday.techswipe.image.infrastructure.ImageData;
import com.nocommittoday.techswipe.image.infrastructure.UrlImageReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageStoreService {

    private final FileStore fileStore;
    private final ImageAppender imageSavePort;
    private final UuidHolder uuidHolder;
    private final UrlImageReader urlImageReader;

    public Image.Id store(final String originUrl, final String dirToStore) {
        log.info("이미지 저장 요청: originUrl={}, dirToStore={}", originUrl, dirToStore);
        final ImageData imageData = urlImageReader.get(originUrl);

        final String storedName = createStoredName(imageData.contentType().ext());
        final String storedUrl = fileStore.store(imageData, Paths.get(dirToStore, storedName).toString());

        return new Image.Id(imageSavePort.save(new ImageSave(storedUrl, originUrl, storedName)));
    }

    private String createStoredName(final String ext) {
        String uuid = uuidHolder.random();
        return uuid + "." + ext;
    }
}
