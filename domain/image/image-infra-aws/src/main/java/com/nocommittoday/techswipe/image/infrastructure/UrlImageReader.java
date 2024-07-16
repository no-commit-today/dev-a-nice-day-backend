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
import java.util.Objects;

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
        final String originUrl = getOriginUrl(url);
        final HttpHeaders headers = requestHeaders(originUrl);

        final MediaType contentType = headers.getContentType();
        if (contentType == null || !ImageContentType.supports(contentType.getType(), contentType.getSubtype())) {
            throw new NotSupportedImageException(originUrl, contentType != null ? contentType.toString() : null);
        }
        final ImageContentType imageContentType = new ImageContentType(contentType.toString());

        if (headers.getContentLength() <= 0) {
            final byte[] imageByteArray = requestImage(originUrl);
            return new ImageData(
                    imageByteArray.length,
                    imageContentType,
                    new ByteArrayResource(imageByteArray)
            );
        }

        return new ImageData(
                headers.getContentLength(),
                imageContentType,
                UrlResource.from(originUrl)
        );
    }

    // 더이상 redirect 하지 않을 때까지 요청 후 최종 URL을 반환
    private String getOriginUrl(final String url) {
        String originUrl = url;
        HttpHeaders headers = requestHeaders(originUrl);
        while (headers.getLocation() != null) {
            originUrl = Objects.requireNonNull(headers.getLocation()).toString();
        }
        return originUrl;
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
