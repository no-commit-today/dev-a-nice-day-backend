package com.devniceday.batch.domain.image;

import com.devniceday.support.aws.Image;
import com.devniceday.support.aws.ImageContentType;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ImageClient {

    private static final String FNAME_QUERY_PARAM_KEY = "fname";

    private static final Pattern URL_DECODING_NEEDS_PATTERN = Pattern.compile(".*" + FNAME_QUERY_PARAM_KEY + "=.*");

    private final RestClient restClient;

    public ImageClient(
            RestClient.Builder restClientBuilder
    ) {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setRedirectStrategy(new DefaultRedirectStrategy()).build()
        );
        requestFactory.setConnectTimeout(Duration.ofMillis(2000));
        requestFactory.setConnectionRequestTimeout(Duration.ofMillis(2000));

        this.restClient = restClientBuilder
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0")
                .requestFactory(requestFactory)
                .build();
    }

    public Image get(String url) {
        try {
            ResponseEntity<Resource> entity = restClient.get()
                    .uri(urlDecodeIfNeeds(url))
                    .retrieve()
                    .toEntity(Resource.class);
            Resource resource = Objects.requireNonNull(entity.getBody());
            HttpHeaders headers = entity.getHeaders();
            ImageContentType contentType = getContentType(headers, url);
            return Image.of(resource, contentType);
        } catch (Exception e) {
            throw new IllegalStateException("이미지 다운로드에 실패했습니다.", e);
        }
    }

    private String urlDecodeIfNeeds(String url) {
        if (!URL_DECODING_NEEDS_PATTERN.matcher(url).matches()) {
            return url;
        }
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);

        UriComponents uriComponents = uriComponentsBuilder.build();

        if (uriComponents.getQueryParams().containsKey(FNAME_QUERY_PARAM_KEY)) {
            uriComponentsBuilder.replaceQueryParam(
                    FNAME_QUERY_PARAM_KEY,
                    uriComponents.getQueryParams().get(FNAME_QUERY_PARAM_KEY).stream()
                            .map(fnameValue -> URLDecoder.decode(fnameValue, StandardCharsets.UTF_8))
                            .toList()
            );
        }
        return uriComponentsBuilder.toUriString();
    }

    private ImageContentType getContentType(HttpHeaders headers, String url) {
        MediaType contentType = headers.getContentType();
        if (contentType == null) {
            return new ImageContentType(null);
        }
        ImageContentType headerImageContentType = ImageContentType.of(contentType.getType(), contentType.getSubtype());

        if (headerImageContentType.supports()) {
            return headerImageContentType;
        }

        if (!MediaType.APPLICATION_OCTET_STREAM.equals(contentType)) {
            return new ImageContentType(null);
        }

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
        MediaType mediaType = MediaTypeFactory.getMediaType(headers.getContentDisposition().getFilename())
                .or(() -> MediaTypeFactory.getMediaType(uriComponents.getPath()))
                .or(() -> uriComponents.getQueryParams().values().stream()
                        .flatMap(Collection::stream)
                        .map(MediaTypeFactory::getMediaType)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .findFirst()
                ).orElse(null);
        if (mediaType == null) {
            return new ImageContentType(null);
        }
        return ImageContentType.of(mediaType.getType(), mediaType.getSubtype());
    }


}
