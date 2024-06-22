package com.nocommittoday.techswipe.image.storage.mysql;

import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.image.domain.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
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

    public static ImageEntity from(final Image.Id id) {
        return new ImageEntity(
                id.value(),
                null,
                null,
                null
        );
    }

    public Image.Id toDomainId() {
        return new Image.Id(id);
    }

    public Image toDomain() {
        return new Image(
                new Image.Id(id),
                url,
                originalUrl,
                storedName
        );
    }
}
