package com.devniceday.storage.db.core;

import com.devniceday.core.enums.TechCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tech_content_category")
public class BatchTechContentCategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "tech_content_id", updatable = false, nullable = false)
    private long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "varchar(45)", updatable = false, nullable = false)
    private TechCategory category;

    protected BatchTechContentCategoryEntity() {
    }

    public BatchTechContentCategoryEntity(long content, TechCategory category) {
        this.contentId = content;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public long getContentId() {
        return contentId;
    }

    public TechCategory getCategory() {
        return category;
    }
}
