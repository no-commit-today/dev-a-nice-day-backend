package com.nocommittoday.techswipe.image.infrastructure;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
@Profile("!local")
public class S3FileStoreAdapter implements FileStore {

    private final S3Template s3Template;
    private final String bucketName;

    public S3FileStoreAdapter(
            final S3Template s3Template,
            @Value("${app.s3.bucket-name}") final String bucketName
    ) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
    }

    @Override
    public String store(final Resource resource, final String storedName) {
        try (final InputStream input = resource.getInputStream()) {
            final String filename = Optional.ofNullable(resource.getFilename())
                    .orElseThrow(() -> new IllegalArgumentException("파일 이름 필요"));
            final String contentType = Files.probeContentType(Path.of(filename));
            final ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType(contentType)
                    .contentLength(resource.contentLength())
                    .build();
            final S3Resource s3Resource = s3Template.upload(bucketName, storedName, input, metadata);
            return s3Resource.getURL().toString();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
