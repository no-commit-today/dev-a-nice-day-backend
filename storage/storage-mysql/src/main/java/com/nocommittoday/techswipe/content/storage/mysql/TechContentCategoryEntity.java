package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.core.storage.mysql.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "tech_content_category",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tech_category__tech_post_id_name",
                        columnNames = {"tech_content_id", "category"}
                )
        }
)
public class TechContentCategoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "tech_content_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentEntity content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "varchar(45)", nullable = false)
    private TechCategory category;

    protected TechContentCategoryEntity() {
    }

    public TechContentCategoryEntity(TechContentEntity content, TechCategory category) {
        this.content = content;
        this.category = category;
    }

    public TechCategory getCategory() {
        return category;
    }

    public TechContentEntity getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }
}
