package com.devniceday.batch.domain.image;

import com.devniceday.batch.domain.UuidHolder;
import com.devniceday.storage.db.core.BatchImageEntity;
import com.devniceday.storage.db.core.BatchImageJpaRepository;
import com.devniceday.support.aws.Image;
import com.devniceday.support.aws.ImageUploader;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Paths;

@Component
public class ContentImageStore {

    private final ImageClient imageClient;
    private final ImageUploader imageUploader;
    private final BatchImageJpaRepository batchImageJpaRepository;
    private final UuidHolder uuidHolder;

    public ContentImageStore(
            ImageClient imageClient,
            ImageUploader imageUploader,
            BatchImageJpaRepository batchImageJpaRepository,
            UuidHolder uuidHolder
    ) {
        this.imageClient = imageClient;
        this.imageUploader = imageUploader;
        this.batchImageJpaRepository = batchImageJpaRepository;
        this.uuidHolder = uuidHolder;
    }

    public long store(String originUrl, String dirToStore) {
        Image image = imageClient.get(originUrl);
        String storedName = createStoredName(image.contentType().ext(), dirToStore);
        URL url = imageUploader.upload(image, "techswipe-images", storedName);

        return batchImageJpaRepository.save(new BatchImageEntity(url.toString(), originUrl, storedName)).getId();
    }

    private String createStoredName(String ext, String dirToStore) {
        String uuid = uuidHolder.random().toString();
        return Paths.get(dirToStore, (uuid + "." + ext)).toString();
    }
}
