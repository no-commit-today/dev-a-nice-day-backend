package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentQuery;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
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
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                @Index(name = "ix_tech_content__published_date", columnList = "published_date desc"),
                @Index(name = "ix_deleted_publisheddate", columnList = "deleted, published_date desc"),
        }
)
public class TechContentEntity extends BaseSoftDeleteEntity implements Persistable<Long> {

    @Id
    @Column(name = "id")
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

    protected TechContentEntity() {
    }

    public TechContentEntity(
            Long id,
            TechContentProviderEntity provider,
            @Nullable ImageEntity image,
            String url,
            String title,
            String summary,
            LocalDate publishedDate
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

    public void addCategory(TechCategory category) {
        if (categories.stream().anyMatch(c -> c.getCategory() == category)) {
            return;
        }
        categories.add(new TechContentCategoryEntity(this, category));
    }

    public TechContentQuery toQuery() {
        return new TechContentQuery(
                new TechContentId(id),
                provider.toQuery(),
                Optional.ofNullable(image)
                        .filter(ImageEntity::isUsed)
                        .map(ImageEntity::getUrl)
                        .orElse(null),
                url,
                title,
                publishedDate,
                new Summary(summary),
                categories.stream().map(TechContentCategoryEntity::getCategory).toList()
        );
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Nullable
    public ImageEntity getImage() {
        return image;
    }

    public TechContentProviderEntity getProvider() {
        return provider;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
