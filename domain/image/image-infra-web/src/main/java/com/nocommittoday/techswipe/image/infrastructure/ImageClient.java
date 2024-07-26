package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import com.nocommittoday.techswipe.core.infrastructure.UuidHolder;
import com.nocommittoday.techswipe.image.domain.ImageContentType;
import com.nocommittoday.techswipe.image.domain.ImageFile;
import lombok.extern.slf4j.Slf4j;
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

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ImageClient {

    private static final File TMP_DIR = Paths.get(System.getProperty("user.dir"), "tmp", "image").toFile();

    private static final String FNAME_QUERY_PARAM_KEY = "fname";

    private static final Pattern URL_DECODING_NEEDS_PATTERN = Pattern.compile(".*" + FNAME_QUERY_PARAM_KEY + "=.*");

    private final RestClient restClient;

    private final UuidHolder uuidHolder;

    public ImageClient(
            final RestClient.Builder restClientBuilder,
            final UuidHolder uuidHolder
    ) {

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setRedirectStrategy(new DefaultRedirectStrategy()).build()
        );
        requestFactory.setConnectTimeout(Duration.ofMillis(2000));

        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();
        this.uuidHolder = uuidHolder;
    }

    public ClientResponse<ImageFile> get(final String url) {
        try {
            final ResponseEntity<Resource> entity = restClient.get()
                    .uri(urlDecodeIfNeeds(url))
                    .retrieve()
                    .toEntity(Resource.class);
            final Resource resource = Objects.requireNonNull(entity.getBody());
            final HttpHeaders headers = entity.getHeaders();
            final File file = downloadFile(resource);
            final ImageContentType contentType = getContentType(headers, url);

            if (!file.renameTo(new File(file.getAbsolutePath() + "." + contentType.ext()))) {
                log.warn("[ImageClient] 파일 확장자 변경에 실패했습니다. file={}", file);
            }
            return ClientResponse.success(new ImageFile(file, contentType));
        } catch (final Exception e) {
            log.error("[ImageClient] url={}", url, e);
            return ClientResponse.failed(e);
        }
    }

    private String urlDecodeIfNeeds(final String url) {
        if (!URL_DECODING_NEEDS_PATTERN.matcher(url).matches()) {
            return url;
        }
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);

        final UriComponents uriComponents = uriComponentsBuilder.build();

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

    private ImageContentType getContentType(final HttpHeaders headers, final String url) {
        final MediaType contentType = headers.getContentType();

        if (isImageType(contentType)) {
            return new ImageContentType(contentType.getType(), contentType.getSubtype());
        }

        if (contentType != null && !MediaType.APPLICATION_OCTET_STREAM.equals(contentType)) {
            throw new NotImageTypeException(url);
        }

        final UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();
        final MediaType mediaType = MediaTypeFactory.getMediaType(headers.getContentDisposition().getFilename())
                .filter(it -> ImageContentType.supports(it.getType(), it.getSubtype()))
                .or(() -> MediaTypeFactory.getMediaType(uriComponents.getPath()))
                .filter(it -> ImageContentType.supports(it.getType(), it.getSubtype()))
                .or(() -> uriComponents.getQueryParams().values().stream()
                        .flatMap(Collection::stream)
                        .map(MediaTypeFactory::getMediaType)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(it -> ImageContentType.supports(it.getType(), it.getSubtype()))
                        .findFirst()
                ).orElseThrow(() -> new NotImageTypeException(url));
        return new ImageContentType(mediaType.getType(), mediaType.getSubtype());
    }

    private boolean isImageType(@Nullable final MediaType contentType) {
        return Optional.ofNullable(contentType)
                .filter(it -> ImageContentType.supports(it.getType(), it.getSubtype()))
                .isPresent();
    }

    private File downloadFile(final Resource resource) {
        createTmpDirIfAbsent();

        final File imageFile = new File(TMP_DIR, uuidHolder.random());
        try (
                final InputStream in = resource.getInputStream();
                final OutputStream out = new FileOutputStream(imageFile);
        ) {
            out.write(in.readAllBytes());
            return imageFile;
        } catch (final IOException e) {
            try {
                Files.deleteIfExists(imageFile.toPath());
            } catch (final IOException ex) {
                log.warn("[ImageClient] 임시 파일 삭제에 실패했습니다. file={}", imageFile, ex);
            }
            throw new UncheckedIOException(e);
        }
    }

    private void createTmpDirIfAbsent() {
        if (!TMP_DIR.exists() && !TMP_DIR.mkdirs()) {
            throw new IllegalStateException("파일 저장을 위한 임시 디렉토리 생성에 실패했습니다.");
        }
    }
}