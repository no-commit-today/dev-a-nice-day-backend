package com.devniceday.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDate;

@Entity
@Table(
        name = "tech_content",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_techcontent__url", columnNames = {"url"})
        }
)
public class TechContentEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "provider_id", nullable = false)
    private long providerId;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    protected TechContentEntity() {
    }

    public long getId() {
        return id;
    }

    public long getProviderId() {
        return providerId;
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
