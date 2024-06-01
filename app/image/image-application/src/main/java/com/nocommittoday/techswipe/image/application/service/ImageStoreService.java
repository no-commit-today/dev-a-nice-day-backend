package com.nocommittoday.techswipe.image.application.service;

import com.nocommittoday.techswipe.core.application.port.out.UuidHolder;
import com.nocommittoday.techswipe.image.application.port.in.ImageStoreUseCase;
import com.nocommittoday.techswipe.image.application.port.out.FileStorePort;
import com.nocommittoday.techswipe.image.application.port.out.ImageSave;
import com.nocommittoday.techswipe.image.application.port.out.ImageSavePort;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.exception.NotSupportedImageException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
class ImageStoreService implements ImageStoreUseCase {

    private final FileStorePort fileStorePort;
    private final ImageSavePort imageSavePort;
    private final UuidHolder uuidHolder;

    @Override
    public Image.ImageId store(final String originUrl, final String dirToStore) {
        final UrlResource resource = UrlResource.from(originUrl);
        final String filename = resource.getFilename();
        final String contentType = getContentType(filename);
        if (!contentType.startsWith("image")) {
            throw new NotSupportedImageException(contentType);
        }
        final String storedName = createStoredName(filename);
        final String storedUrl = fileStorePort.store(resource, Paths.get(dirToStore, storedName).toString());

        return new Image.ImageId(imageSavePort.save(new ImageSave(storedUrl, originUrl, storedName)));
    }

    private String getContentType(final String filename) {
        try {
            return Files.probeContentType(Paths.get(filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String createStoredName(final String originalName) {
        String uuid = uuidHolder.random();
        String ext = extractExt(originalName);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
