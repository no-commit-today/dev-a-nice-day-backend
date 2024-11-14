package com.devniceday.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "tech_content_s3_image",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_contentid", columnNames = {"content_id"})
        }
)
public class TechContentS3ImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id", nullable = false)
    private long contentId;

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    protected TechContentS3ImageEntity() {
    }

    public Long getId() {
        return id;
    }

    public long getContentId() {
        return contentId;
    }

    public String getUrl() {
        return url;
    }
}
