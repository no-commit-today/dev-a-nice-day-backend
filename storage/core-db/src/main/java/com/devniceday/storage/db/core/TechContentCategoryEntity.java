package com.devniceday.storage.db.core;

import com.devniceday.core.enums.TechCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "tech_content_category",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_techcontentcategory__techcontentid_category",
                        columnNames = {"tech_content_id", "category"}
                )
        }
)
public class TechContentCategoryEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "tech_content_id", nullable = false)
    private long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "varchar(45)", nullable = false)
    private TechCategory category;

    protected TechContentCategoryEntity() {
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
