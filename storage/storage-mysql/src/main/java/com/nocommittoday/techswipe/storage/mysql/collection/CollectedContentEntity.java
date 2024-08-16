package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.data.domain.Persistable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
        name = "collected_content",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_collected_content__url", columnNames = {"url"})
        }
)
public class CollectedContentEntity extends BaseSoftDeleteEntity implements Persistable<Long> {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(45)", nullable = false)
    private CollectionStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @Lob
    @Column(name = "content", length = 100_000_000, nullable = false)
    private String content;

    @Nullable
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Nullable
    @Convert(converter = CategoryListConverter.class)
    @Column(name = "categories", length = 500)
    private List<CollectionCategory> categories;

    @Nullable
    @Column(name = "summary", length = 2_000)
    private String summary;

    protected CollectedContentEntity() {
    }

    public CollectedContentEntity(
            Long id,
            CollectionStatus status,
            TechContentProviderEntity provider,
            String url,
            String title,
            LocalDate publishedDate,
            String content,
            @Nullable String imageUrl,
            @Nullable List<CollectionCategory> categories,
            @Nullable String summary
    ) {
        this.id = id;
        this.status = status;
        this.provider = provider;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.summary = summary;
    }

    public CollectedContent toDomain() {
        return new CollectedContent(
                new CollectedContentId(id),
                status,
                categories != null ? new CollectionCategoryList(categories) : null,
                new Summary(summary),
                provider.getId() != null ? new TechContentProviderId(provider.getId()) : null,
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    public void update(CollectedContent collectedContent) {
        this.status = collectedContent.getStatus();
        this.provider = TechContentProviderEntity.from(collectedContent.getProviderId());
        this.url = collectedContent.getUrl();
        this.title = collectedContent.getTitle();
        this.publishedDate = collectedContent.getPublishedDate();
        this.content = collectedContent.getContent();
        this.imageUrl = collectedContent.getImageUrl();
        this.categories = collectedContent.getCategoryList() != null
                ? collectedContent.getCategoryList().getContent() : null;
        this.summary = collectedContent.getSummary() != null ? collectedContent.getSummary().getContent() : null;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Nullable
    public List<CollectionCategory> getCategories() {
        return categories;
    }

    public String getContent() {
        return content;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public TechContentProviderEntity getProvider() {
        return provider;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    @Nullable
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
