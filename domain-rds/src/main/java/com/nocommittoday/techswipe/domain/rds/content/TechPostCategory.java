package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.techswipe.domain.rds.core.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "tech_post_category",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tech_post_category__tech_post_id_name",
                        columnNames = {"tech_post_id", "category"}
                )
        }
)
public class TechPostCategory extends BaseIdEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "tech_post_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechPost post;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 45, nullable = false)
    private TechCategory category;

    protected TechPostCategory() {
    }

    TechPostCategory(final TechPost post, final TechCategory category) {
        this.post = post;
        this.category = category;
    }

    public TechPost getPost() {
        return post;
    }

    public TechCategory getCategory() {
        return category;
    }
}
