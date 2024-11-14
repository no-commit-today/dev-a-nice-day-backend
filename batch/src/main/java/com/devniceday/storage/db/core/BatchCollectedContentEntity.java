package com.devniceday.storage.db.core;

import com.devniceday.batch.domain.CollectionCategory;
import com.devniceday.batch.domain.CollectionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
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
                @UniqueConstraint(name = "uk_url", columnNames = {"url"})
        },
        indexes = {
                @Index(name = "ix_deleted_status", columnList = "deleted, status"),
        }
)
public class BatchCollectedContentEntity extends BaseEntity implements Persistable<Long> {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(45)", nullable = false)
    private CollectionStatus status;

    @Column(name = "provider_id", updatable = false, nullable = false)
    private long providerId;

    @Column(name = "subscription_id", updatable = false, nullable = false)
    private long subscriptionId;

    @Column(name = "url", length = 500, updatable = false, nullable = false)
    private String url;

    @Nullable
    @Column(name = "title", length = 500)
    private String title;

    @Nullable
    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Nullable
    @Lob
    @Column(name = "content", length = 100_000_000)
    private String content;

    @Nullable
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Nullable
    @Convert(converter = CollectionCategoryListToStringConverter.class)
    @Column(name = "categories", length = 500)
    private List<CollectionCategory> categories;

    @Nullable
    @Column(name = "summary", length = 2_000)
    private String summary;

    @Nullable
    @Column(name = "published_content_id")
    private Long publishedContentId;

    protected BatchCollectedContentEntity() {
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    public long getProviderId() {
        return providerId;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public String getUrl() {
        return url;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public List<CollectionCategory> getCategories() {
        return categories;
    }

    @Nullable
    public String getSummary() {
        return summary;
    }

    @Nullable
    public Long getPublishedContentId() {
        return publishedContentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategories(List<CollectionCategory> categories) {
        this.categories = categories;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPublishedContentId(Long publishedContentId) {
        this.publishedContentId = publishedContentId;
    }
}
