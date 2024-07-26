package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.core.infrastructure.UuidHolder;
import com.nocommittoday.techswipe.image.domain.ImageFile;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.domain.ImageSave;
import com.nocommittoday.techswipe.image.infrastructure.FileStore;
import com.nocommittoday.techswipe.image.infrastructure.ImageAppender;
import com.nocommittoday.techswipe.image.infrastructure.ImageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageStoreService {

    private final FileStore fileStore;
    private final ImageAppender imageAppender;
    private final UuidHolder uuidHolder;
    private final ImageClient imageClient;

    public ImageStoreResult store(final String originUrl, final String dirToStore) {
        log.info("이미지 저장 요청: originUrl={}, dirToStore={}", originUrl, dirToStore);
        final ClientResponse<ImageFile> clientResponse = imageClient.get(originUrl);

        if (!clientResponse.isSuccess()) {
            return ImageStoreResult.fail(clientResponse.getException());
        }
        final ImageFile imageFile = clientResponse.get();

        final String storedName = createStoredName(imageFile.getContentType().ext(), dirToStore);
        final String storedUrl = fileStore.store(imageFile, storedName);

        return ImageStoreResult.success(
                new ImageId(imageAppender.save(new ImageSave(storedUrl, originUrl, storedName)))
        );
    }

    private String createStoredName(final String ext, final String dirToStore) {
        final String uuid = uuidHolder.random();
        return Paths.get(dirToStore, (uuid + "." + ext)).toString();
    }
}
