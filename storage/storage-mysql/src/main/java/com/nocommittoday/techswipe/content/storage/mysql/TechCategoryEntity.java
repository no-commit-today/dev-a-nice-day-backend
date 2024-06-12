package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import com.nocommittoday.techswipe.common.storage.mysql.BaseTimeEntity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_category",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tech_category__tech_post_id_name",
                        columnNames = {"tech_content_id", "category"}
                )
        }
)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TechCategoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "tech_content_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentEntity content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", columnDefinition = "varchar(45)", nullable = false)
    private TechCategory category;

    public TechCategoryEntity(final TechContentEntity content, final TechCategory category) {
        this.content = content;
        this.category = category;
    }
}
