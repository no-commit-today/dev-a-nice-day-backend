package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.exception.ImageIllegalUrlException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ContentTypeReader {

    private final RestTemplate restTemplate;

    public ContentTypeReader(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getContentType(final String url) {
        final HttpHeaders httpHeaders = requestHeaders(url);
        return Optional.of(httpHeaders)
                .map(HttpHeaders::getContentType)
                .map(MediaType::toString)
                .orElseThrow(() -> new ImageIllegalUrlException(url));
    }

    private HttpHeaders requestHeaders(final String url) {
        try {
            return restTemplate.headForHeaders(url);
        } catch (final HttpClientErrorException ex) {
            if (ex.getStatusCode().is4xxClientError()) {
                throw new ImageIllegalUrlException(url);
            }
            throw ex;
        }
    }
}
