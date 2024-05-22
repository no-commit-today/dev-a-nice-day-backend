package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.domain.rds.image.UrlImage;
import com.nocommittoday.techswipe.domain.rds.image.repository.UrlImageRepository;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UrlImageService {

    private final FileStore fileStore;

    private final UrlImageRepository urlImageRepository;

    public UrlImageService(final FileStore fileStore, final UrlImageRepository urlImageRepository) {
        this.fileStore = fileStore;
        this.urlImageRepository = urlImageRepository;
    }

    public Long saveImage(final String url, final String dir) {
        final UrlResource resource = UrlResource.from(url);
        final String filename = resource.getFilename();
        final String contentType = getContentType(filename);
        if (!contentType.startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일이 아님");
        }

        final String storedName = createStoredName(filename);
        final String storedUrl = fileStore.store(resource, Paths.get(dir, storedName).toString());
        return urlImageRepository.save(UrlImage.builder()
                .url(storedUrl)
                .originalUrl(url)
                .storedName(storedName)
                .build()
        ).getId();
    }

    private String getContentType(final String filename) {
        try {
            return Files.probeContentType(Paths.get(filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String createStoredName(final String originalName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalName);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
