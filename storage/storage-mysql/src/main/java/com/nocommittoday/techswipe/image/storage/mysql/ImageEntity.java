package com.nocommittoday.techswipe.image.storage.mysql;

import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.domain.ImageSync;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ImageEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @Column(name = "original_url", length = 1000, nullable = false)
    private String originalUrl;

    @Column(name = "stored_name", length = 1000, nullable = false)
    private String storedName;

    public ImageEntity(
            @Nullable final Long id,
            final String url,
            final String originalUrl,
            final String storedName
    ) {
        this.id = id;
        this.url = url;
        this.originalUrl = originalUrl;
        this.storedName = storedName;
    }

    public static ImageEntity from(final ImageId id) {
        return new ImageEntity(
                id.value(),
                null,
                null,
                null
        );
    }

    public static ImageEntity from(final ImageSync imageSync) {
        final ImageEntity entity = new ImageEntity(
                imageSync.id().value(),
                imageSync.url(),
                imageSync.originalUrl(),
                imageSync.storedName()
        );
        if (imageSync.deleted()) {
            entity.delete();
        }
        return entity;
    }

    public Image toDomain() {
        return new Image(
                new ImageId(id),
                url,
                originalUrl,
                storedName
        );
    }

    public ImageSync toSync() {
        return new ImageSync(
                new ImageId(id),
                url,
                originalUrl,
                storedName,
                isDeleted()
        );
    }
}
