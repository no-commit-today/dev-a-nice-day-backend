package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageContentType;
import com.nocommittoday.techswipe.image.domain.NotSupportedImageException;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class UrlImageReader {

    private final RestClient restClient;

    public UrlImageReader(final RestClient.Builder restClientBuilder) {
        final ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(
                ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofMillis(2000))
        );
        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();
    }

    public ImageData get(final String url) {
        final HttpHeaders headers = requestHeaders(url);

        final MediaType contentType = headers.getContentType();
        if (contentType == null || ImageContentType.supports(contentType.getType(), contentType.getSubtype())) {
            throw new NotSupportedImageException(url, contentType != null ? contentType.toString() : null);
        }
        final ImageContentType imageContentType = new ImageContentType(contentType.toString());

        if (headers.getContentLength() <= 0) {
            final byte[] imageByteArray = requestImage(url);
            return new ImageData(
                    imageByteArray.length,
                    imageContentType,
                    new ByteArrayResource(imageByteArray)
            );
        }

        return new ImageData(
                headers.getContentLength(),
                imageContentType,
                UrlResource.from(url)
        );
    }

    private HttpHeaders requestHeaders(final String url) {
        return restClient.head()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .getHeaders();
    }

    private byte[] requestImage(final String url) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(byte[].class)
                .getBody();
    }

}
