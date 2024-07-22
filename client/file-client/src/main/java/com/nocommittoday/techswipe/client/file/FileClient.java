package com.nocommittoday.techswipe.client.file;

import com.nocommittoday.techswipe.client.core.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FileClient {

    private static final File TMP_DIR = Paths.get(System.getProperty("user.dir"), "tmp", "file").toFile();

    private static final String FNAME_QUERY_PARAM_KEY = "fname";

    private static final Pattern URL_DECODING_NEEDS_PATTERN = Pattern.compile(".*" + FNAME_QUERY_PARAM_KEY + "=.*");

    private final RestClient restClient;

    public FileClient(final RestClient.Builder restClientBuilder) {

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setRedirectStrategy(new DefaultRedirectStrategy()).build()
        );
        requestFactory.setConnectTimeout(Duration.ofMillis(2000));

        this.restClient = restClientBuilder
                .requestFactory(requestFactory)
                .build();
    }

    public ClientResponse<FileResponse> get(final String url) {
        try {
            final String requestUrl = urlDecodingIfNeed(url);

            final ResponseEntity<byte[]> entity = restClient.get()
                    .uri(requestUrl)
                    .retrieve()
                    .toEntity(byte[].class);

            createTmpDirIfAbsent();
            final String contentType = getContentType(requestUrl, entity.getHeaders().getContentType());
            final File filePath = new File(TMP_DIR, UUID.randomUUID().toString());

            return getResponse(entity, filePath, contentType);
        } catch (final Exception e) {
            log.error("[FileClient] url={}", url, e);
            return ClientResponse.failed(e);
        }
    }

    private String urlDecodingIfNeed(final String url) {
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

    private static ClientResponse<FileResponse> getResponse(final ResponseEntity<byte[]> entity, final File filePath, final String contentType) {
        try (
                final InputStream in = new ByteArrayInputStream(Objects.requireNonNull(entity.getBody()));
                final OutputStream out = new FileOutputStream(filePath)
        ) {
            out.write(in.readAllBytes());
            return ClientResponse.success(new FileResponse(filePath, contentType));
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String getContentType(final String url, @Nullable final MediaType contentType) {
        return Optional.ofNullable(contentType)
                .map(it -> it.getType() + "/" + it.getSubtype())
                .orElseGet(() -> URLConnection.guessContentTypeFromName(url));
    }

    private void createTmpDirIfAbsent() {
        if (!TMP_DIR.exists() && !TMP_DIR.mkdirs()) {
            throw new IllegalStateException("파일 저장을 위한 임시 디렉토리 생성에 실패했습니다.");
        }
    }
}
