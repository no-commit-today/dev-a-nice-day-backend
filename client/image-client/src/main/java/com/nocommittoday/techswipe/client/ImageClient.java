package com.nocommittoday.techswipe.client;

import com.nocommittoday.techswipe.client.core.ClientResponse;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ImageClient {

    private static final File TMP_DIR = Paths.get(System.getProperty("user.dir"), "tmp", "image").toFile();

    private static final String IMAGE_CONTENT_TYPE = "image";

    private final RestClient restClient;

    public ImageClient(
            final RestClient.Builder restClientBuilder
    ) {

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setRedirectStrategy(new DefaultRedirectStrategy()).build()
        );
        requestFactory.setConnectTimeout(Duration.ofMillis(2000));

        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();
    }

    public ClientResponse<ImageFileResponse> get(final String url) {
        try {
            final ResponseEntity<Resource> entity = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(Resource.class);
            final Resource resource = Objects.requireNonNull(entity.getBody());
            final File file = downloadFile(resource);

            final HttpHeaders headers = entity.getHeaders();
            final MediaType contentType = getContentType(headers, url);
            return ClientResponse.success(new ImageFileResponse(file, contentType));
        } catch (final Exception e) {
            log.error("[ImageClient] url={}", url, e);
            return ClientResponse.failed(e);
        }
    }

    private MediaType getContentType(final HttpHeaders headers, final String url) {
        final MediaType contentType = headers.getContentType();
        final boolean isImageType = Optional.ofNullable(contentType)
                .map(MediaType::getType)
                .filter(IMAGE_CONTENT_TYPE::equals)
                .isPresent();
        if (isImageType) {
            return contentType;
        }

        if (!MediaType.APPLICATION_OCTET_STREAM.equals(contentType) && contentType != null) {
            throw new NotImageTypeException(url);
        }

        final UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).build();

        return MediaTypeFactory.getMediaType(uriComponents.getPath())
                .or(() -> uriComponents.getQueryParams().values().stream()
                        .flatMap(Collection::stream)
                        .map(MediaTypeFactory::getMediaType)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(mediaType -> IMAGE_CONTENT_TYPE.equals(mediaType.getType()))
                        .findFirst()
                ).orElseThrow(() -> new NotImageTypeException(url));
    }


    private File downloadFile(final Resource resource) {
        createTmpDirIfAbsent();

        final File imageFile = new File(TMP_DIR, UUID.randomUUID().toString());
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
