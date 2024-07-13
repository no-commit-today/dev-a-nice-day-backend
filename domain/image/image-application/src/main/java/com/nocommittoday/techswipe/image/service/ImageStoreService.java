package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.core.infrastructure.UuidHolder;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageSave;
import com.nocommittoday.techswipe.image.infrastructure.ContentTypeReader;
import com.nocommittoday.techswipe.image.infrastructure.FileStore;
import com.nocommittoday.techswipe.image.infrastructure.ImageAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageStoreService {

    private static final Map<String, String> mimeToExt = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/gif", "gif",
            "image/bmp", "bmp",
            "image/webp", "webp",
            "image/svg+xml", "svg",
            "image/tiff", "tif",
            "image/x-icon", "ico",
            "image/vnd.microsoft.icon", "ico"
    );

    private final FileStore fileStore;
    private final ImageAppender imageSavePort;
    private final ContentTypeReader contentTypeReader;
    private final UuidHolder uuidHolder;

    public Image.Id store(final String originUrl, final String dirToStore) {
        final Resource resource = UrlResource.from(originUrl);
        final String contentType = contentTypeReader.getContentType(originUrl);

        final String storedName = createStoredName(mimeToExt.get(contentType));
        final String storedUrl = fileStore.store(resource, Paths.get(dirToStore, storedName).toString());

        return new Image.Id(imageSavePort.save(new ImageSave(storedUrl, originUrl, storedName)));
    }

    private String createStoredName(final String ext) {
        String uuid = uuidHolder.random();
        return uuid + "." + ext;
    }
}
