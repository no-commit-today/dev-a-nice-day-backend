package com.nocommittoday.techswipe.infrastructure.aws.image;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.domain.core.UuidHolder;
import com.nocommittoday.techswipe.domain.image.ImageFile;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class ImageStore {

    private static final Logger log = LoggerFactory.getLogger(ImageStore.class);

    private final FileStore fileStore;
    private final ImageAppender imageAppender;
    private final UuidHolder uuidHolder;
    private final ImageClient imageClient;

    public ImageStore(
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
        String uuid = uuidHolder.random().toString();
        return Paths.get(dirToStore, (uuid + "." + ext)).toString();
    }
}
