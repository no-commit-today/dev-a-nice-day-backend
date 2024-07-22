package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.client.file.FileClient;
import com.nocommittoday.techswipe.client.file.FileResponse;
import com.nocommittoday.techswipe.image.domain.ImageContentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UrlImageReader {

    private final FileClient fileClient;

    public UrlImageResponse get(final String url) {
        final ClientResponse<FileResponse> response = fileClient.get(url);
        if (!response.isSuccess()) {
            return UrlImageResponse.failure(response.getException());
        }

        try {
            final FileResponse fileResponse = response.get();
            return UrlImageResponse.success(
                    new ImageData(new ImageContentType(fileResponse.contentType()), fileResponse.file())
            );
        } catch (final Exception e) {
            return UrlImageResponse.failure(e);
        }
    }

}
