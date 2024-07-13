package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.ImageIllegalUrlException;
import com.nocommittoday.techswipe.image.domain.NotSupportedImageException;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class ContentTypeReader {

    private final RestClient restClient;

    public ContentTypeReader(final RestClient.Builder restClientBuilder) {
        final ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(
                ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofMillis(2000))
        );
        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();
    }

    public String getContentType(final String url) {
        final HttpHeaders httpHeaders = requestHeaders(url);
        final MediaType contentType = httpHeaders.getContentType();
        if (contentType == null || !"image".equals(contentType.getType())) {
            throw new NotSupportedImageException(url, contentType != null ? contentType.toString() : null);
        }

        return contentType.getType() + "/" + contentType.getSubtype();
    }

    private HttpHeaders requestHeaders(final String url) {
        try {
            return restClient.head()
                    .uri(url)
                    .retrieve()
                    .toBodilessEntity()
                    .getHeaders();
        } catch (final HttpClientErrorException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new ImageIllegalUrlException(url);
            }
            throw ex;
        }
    }
}
