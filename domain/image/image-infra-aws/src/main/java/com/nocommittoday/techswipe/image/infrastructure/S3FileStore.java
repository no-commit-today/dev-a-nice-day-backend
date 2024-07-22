package com.nocommittoday.techswipe.image.infrastructure;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@Component
@ConditionalOnProperty(name = "app.image.s3.enabled", havingValue = "true")
public class S3FileStore implements FileStore {

    private final S3Template s3Template;
    private final String bucketName;

    public S3FileStore(
            final S3Template s3Template,
            @Value("${app.image.s3.bucket-name}") final String bucketName
    ) {
        this.s3Template = s3Template;
        this.bucketName = bucketName;
    }

    @Override
    public String store(final ImageData imageData, final String storedName) {
        try (final InputStream input = new FileInputStream(imageData.file())) {
            final ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType(imageData.contentType().value())
                    .contentLength(imageData.file().length())
                    .build();
            final S3Resource s3Resource = s3Template.upload(bucketName, storedName, input, metadata);
            return s3Resource.getURL().toString();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            imageData.file().delete();
        }
    }
}
