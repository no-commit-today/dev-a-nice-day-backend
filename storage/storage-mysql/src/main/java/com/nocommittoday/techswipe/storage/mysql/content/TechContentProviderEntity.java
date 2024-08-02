package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechContentProvider;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderType;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
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
import org.springframework.data.domain.Persistable;

@Entity
@Table(
        name = "tech_content_provider",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_content_provider__url", columnNames = {"url"})
        }
)
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

    protected TechContentProviderEntity() {
    }

    public TechContentProviderEntity(
            Long id,
            TechContentProviderType type,
            String title,
            String url,
            @Nullable ImageEntity icon
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.url = url;
        this.icon = icon;
    }

    public static TechContentProviderEntity from(TechContentProviderId id) {
        return new TechContentProviderEntity(
                id.value(),
                null,
                null,
                null,
                null
        );
    }

    public TechContentProvider toDomain() {
        return new TechContentProvider(
                new TechContentProviderId(id),
                type,
                title,
                url,
                icon == null ? null : new ImageId(icon.getId())
        );
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Nullable
    public ImageEntity getIcon() {
        return icon;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TechContentProviderType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
