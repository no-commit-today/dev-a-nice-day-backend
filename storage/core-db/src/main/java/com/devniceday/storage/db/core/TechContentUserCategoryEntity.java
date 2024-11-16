package com.devniceday.storage.db.core;

import com.devniceday.core.enums.TechCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.List;

@Entity
@Table(
        name = "tech_content_user_category",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_techcontenetusercategory__userid", columnNames = "user_id")
        }
)
public class TechContentUserCategoryEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Convert(converter = TechCategoryListToStringConverter.class)
    @Column(name = "categories", length = 500)
    private List<TechCategory> categories;

    protected TechContentUserCategoryEntity() {
    }

    public TechContentUserCategoryEntity(
            long userId,
            List<TechCategory> categories
    ) {
        this.userId = userId;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TechCategory> categories) {
        this.categories = categories;
    }
}
