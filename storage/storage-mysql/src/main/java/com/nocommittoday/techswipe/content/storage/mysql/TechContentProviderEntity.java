package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_content_provider",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_content_provider__url", columnNames = {"url"})
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TechContentProviderEntity extends BaseSoftDeleteEntity implements Persistable<Long> {

    @Id
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private TechContentProviderType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageEntity icon;

    public TechContentProviderEntity(
            final Long id,
            final TechContentProviderType type,
            final String title,
            final String url,
            @Nullable final ImageEntity icon
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
        this.icon = icon;
    }

    public static TechContentProviderEntity from(final TechContentProvider.Id id) {
        return new TechContentProviderEntity(
                id.value(),
                null,
                null,
                null,
                null
        );
    }

    public TechContentProvider.Id toDomainId() {
        return new TechContentProvider.Id(id);
    }

    public TechContentProvider toDomain() {
        return new TechContentProvider(
                toDomainId(),
                type,
                title,
                url,
                icon == null ? null : new Image.Id(icon.getId())
        );
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
