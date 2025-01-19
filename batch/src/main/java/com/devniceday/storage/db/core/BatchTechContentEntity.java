package com.devniceday.storage.db.core;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;

@Entity
@Table(name = "tech_content")
public class BatchTechContentEntity extends BaseEntity implements Persistable<Long> {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "provider_id", nullable = false)
    private long providerId;

    @Nullable
    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    protected BatchTechContentEntity() {
    }

    public BatchTechContentEntity(
            Long id,
            long providerId,
            @Nullable Long imageId,
            String url,
            String title,
            String summary,
            LocalDate publishedDate
    ) {
        this.id = id;
        this.providerId = providerId;
        this.imageId = imageId;
        this.url = url;
        this.title = title;
        this.summary = summary;
        this.publishedDate = publishedDate;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public long getProviderId() {
        return providerId;
    }

    @Nullable
    public Long getImageId() {
        return imageId;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }
}
