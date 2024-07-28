package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.content.domain.TechContentCreate;
import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.domain.TechContentSync;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_content",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_content__url", columnNames = {"url"})
        },
        indexes = {
                @Index(
                        name = "ix_tech_content__provider_published_date",
                        columnList = "provider_id, published_date desc"
                ),
                @Index(name = "ix_tech_content__published_date", columnList = "published_date desc")
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TechContentEntity extends BaseSoftDeleteEntity implements Persistable<Long> {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageEntity image;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @OneToMany(mappedBy = "content", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TechContentCategoryEntity> categories = new ArrayList<>();

    public TechContentEntity(
            final Long id,
            final TechContentProviderEntity provider,
            @Nullable final ImageEntity image,
            final String url,
            final String title,
            final String summary,
            final LocalDate publishedDate
    ) {
        this.id = id;
        this.image = image;
        this.provider = provider;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.title = title;
        this.url = url;
    }

    public List<TechCategory> getCategories() {
        return categories.stream()
                .map(TechContentCategoryEntity::getCategory)
                .toList();
    }

    public List<TechContentCategoryEntity> getCategoryEntities() {
        return Collections.unmodifiableList(categories);
    }

    public void addCategory(final TechCategory category) {
        if (categories.stream().anyMatch(c -> c.getCategory() == category)) {
            return;
        }
        categories.add(new TechContentCategoryEntity(this, category));
    }

    public static TechContentEntity from(final TechContentCreate techContentCreate) {
        final TechContentEntity entity = new TechContentEntity(
                techContentCreate.id().value(),
                TechContentProviderEntity.from(techContentCreate.providerId()),
                techContentCreate.imageId() == null ? null : ImageEntity.from(techContentCreate.imageId()),
                techContentCreate.url(),
                techContentCreate.title(),
                techContentCreate.summary(),
                techContentCreate.publishedDate()
        );
        techContentCreate.categories().forEach(entity::addCategory);
        return entity;
    }

    public static TechContentEntity from(final TechContentSync contentSync) {
        final TechContentEntity entity = new TechContentEntity(
                contentSync.id().value(),
                TechContentProviderEntity.from(contentSync.providerId()),
                contentSync.imageId() == null ? null : ImageEntity.from(contentSync.imageId()),
                contentSync.url(),
                contentSync.title(),
                contentSync.summary(),
                contentSync.publishedDate()
        );
        contentSync.categories().forEach(entity::addCategory);

        if (contentSync.deleted()) {
            entity.delete();
        }

        return entity;
    }

    public TechContent toDomain() {
        return new TechContent(
                new TechContentId(id),
                provider.toDomain(),
                image == null ? null : new ImageId(image.getId()),
                url,
                title,
                publishedDate,
                summary,
                categories.stream().map(TechContentCategoryEntity::getCategory).toList()
        );
    }

    public TechContentSync toSync() {
        return new TechContentSync(
                new TechContentId(id),
                new TechContentProviderId(Objects.requireNonNull(provider.getId())),
                image == null ? null : new ImageId(image.getId()),
                url,
                title,
                summary,
                publishedDate,
                categories.stream().map(TechContentCategoryEntity::getCategory).toList(),
                isDeleted()
        );
    }

    public void update(final TechContent techContent) {
        this.provider = TechContentProviderEntity.from(techContent.getProvider().getId());
        this.image = techContent.getImageId() == null ? null : ImageEntity.from(techContent.getImageId());
        this.url = techContent.getUrl();
        this.title = techContent.getTitle();
        this.summary = techContent.getSummary();
        this.publishedDate = techContent.getPublishedDate();
        this.categories = techContent.getCategories().stream()
                .map(category -> new TechContentCategoryEntity(this, category))
                .toList();
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
