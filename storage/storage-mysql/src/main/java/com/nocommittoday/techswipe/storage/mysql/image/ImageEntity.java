package com.nocommittoday.techswipe.storage.mysql.image;

import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.domain.image.Image;
import com.nocommittoday.techswipe.domain.image.ImageId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import javax.annotation.Nullable;
import java.util.Optional;

@Entity
@Table(name = "images")
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

    public static String url(@Nullable ImageEntity entity) {
        return Optional.ofNullable(entity)
                .filter(ImageEntity::isUsed)
                .map(ImageEntity::getUrl)
                .orElse(null);
    }

    protected ImageEntity() {
    }

    public ImageEntity(
            @Nullable Long id,
            String url,
            String originalUrl,
            String storedName
    ) {
        this.id = id;
        this.url = url;
        this.originalUrl = originalUrl;
        this.storedName = storedName;
    }

    public Image toDomain() {
        return new Image(
                new ImageId(id),
                url,
                originalUrl,
                storedName
        );
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getStoredName() {
        return storedName;
    }

    public String getUrl() {
        return url;
    }
}
