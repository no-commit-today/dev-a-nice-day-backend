package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.storage.mysql.core.BaseTimeEntity;
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
                @UniqueConstraint(name = "uk_userid", columnNames = "user_id")
        }
)
public class TechContentUserCategoryEntity extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Convert(converter = TechCategoryListToStringConverter.class)
    @Column(name = "categories", length = 500)
    private List<TechCategory> categories;

    protected TechContentUserCategoryEntity() {
    }

    public TechContentUserCategoryEntity(
            Long userId,
            List<TechCategory> categories
    ) {
        this.userId = userId;
        this.categories = categories;
    }

    public TechContentUserCategoryEntityEditor toEditor() {
        return new TechContentUserCategoryEntityEditor(categories);
    }

    public void edit(TechContentUserCategoryEntityEditor editor) {
        this.categories = editor.getCategories();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }
}
