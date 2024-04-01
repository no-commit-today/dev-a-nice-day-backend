package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.techswipe.domain.rds.core.BaseIdEntity;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "tech_post_keyword",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_tech_post_keyword__post_id_keyword_id",
                        columnNames = {"tech_post_id", "keyword_id"}
                )
        }
)
public class TechPostKeyword extends BaseIdEntity {

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "tech_post_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechPost post;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "keyword_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechKeyword keyword;

    protected TechPostKeyword() {
    }

    TechPostKeyword(final TechPost post, final TechKeyword keyword) {
        this.post = post;
        this.keyword = keyword;
    }

    public TechPost getPost() {
        return post;
    }

    public TechKeyword getKeyword() {
        return keyword;
    }
}
