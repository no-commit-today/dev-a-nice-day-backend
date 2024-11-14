package com.devniceday.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tech_content_s3_image")
public class BatchTechContentS3ImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id", nullable = false)
    private long contentId;

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @Column(name = "original_url", length = 1000, nullable = false)
    private String originalUrl;

    @Column(name = "stored_name", length = 1000, nullable = false)
    private String storedName;

    protected BatchTechContentS3ImageEntity() {
    }

    public BatchTechContentS3ImageEntity(long contentId, String url, String originalUrl, String storedName) {
        this.contentId = contentId;
        this.url = url;
        this.originalUrl = originalUrl;
        this.storedName = storedName;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getStoredName() {
        return storedName;
    }
}
