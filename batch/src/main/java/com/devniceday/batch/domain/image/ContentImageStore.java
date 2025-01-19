package com.devniceday.batch.domain.image;

import com.devniceday.batch.domain.UuidHolder;
import com.devniceday.storage.db.core.BatchImageEntity;
import com.devniceday.storage.db.core.BatchImageJpaRepository;
import com.devniceday.support.aws.Image;
import com.devniceday.support.aws.ImageUploader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Paths;

@Component
public class ContentImageStore {

    private final ImageClient imageClient;
    private final ImageUploader imageUploader;
    private final BatchImageJpaRepository batchImageJpaRepository;
    private final UuidHolder uuidHolder;
    private final String bucketName;

    public ContentImageStore(
            ImageClient imageClient,
            ImageUploader imageUploader,
            BatchImageJpaRepository batchImageJpaRepository,
            UuidHolder uuidHolder,
            @Value("${app.image.s3.bucket-name}") String bucketName
    ) {
        this.imageClient = imageClient;
        this.imageUploader = imageUploader;
        this.batchImageJpaRepository = batchImageJpaRepository;
        this.uuidHolder = uuidHolder;
        this.bucketName = bucketName;
    }

    public long store(String originUrl, String dirToStore) {
        Image image = imageClient.get(originUrl);
        String storedName = createStoredName(image.contentType().ext(), dirToStore);
        URL url = imageUploader.upload(image, bucketName, storedName);

        return batchImageJpaRepository.save(new BatchImageEntity(url.toString(), originUrl, storedName)).getId();
    }

    private String createStoredName(String ext, String dirToStore) {
        String uuid = uuidHolder.random().toString();
        return Paths.get(dirToStore, (uuid + "." + ext)).toString();
    }
}
