package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.core.infrastructure.UuidHolder;
import com.nocommittoday.techswipe.image.domain.ImageFile;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.domain.ImageSave;
import com.nocommittoday.techswipe.image.infrastructure.FileStore;
import com.nocommittoday.techswipe.image.infrastructure.ImageAppender;
import com.nocommittoday.techswipe.image.infrastructure.ImageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class ImageStoreService {

    private static final Logger log = LoggerFactory.getLogger(ImageStoreService.class);

    private final FileStore fileStore;
    private final ImageAppender imageAppender;
    private final UuidHolder uuidHolder;
    private final ImageClient imageClient;

    public ImageStoreService(
            FileStore fileStore, ImageAppender imageAppender, UuidHolder uuidHolder, ImageClient imageClient
    ) {
        this.fileStore = fileStore;
        this.imageAppender = imageAppender;
        this.uuidHolder = uuidHolder;
        this.imageClient = imageClient;
    }

    public ImageStoreResult store(String originUrl, String dirToStore) {
        log.info("이미지 저장 요청: originUrl={}, dirToStore={}", originUrl, dirToStore);
        ClientResponse<ImageFile> clientResponse = imageClient.get(originUrl);

        if (!clientResponse.isSuccess()) {
            return ImageStoreResult.fail(clientResponse.getException());
        }
        ImageFile imageFile = clientResponse.get();

        String storedName = createStoredName(imageFile.getContentType().ext(), dirToStore);
        String storedUrl = fileStore.store(imageFile, storedName);

        return ImageStoreResult.success(
                new ImageId(imageAppender.save(new ImageSave(storedUrl, originUrl, storedName)))
        );
    }

    private String createStoredName(String ext, String dirToStore) {
        String uuid = uuidHolder.random();
        return Paths.get(dirToStore, (uuid + "." + ext)).toString();
    }
}
