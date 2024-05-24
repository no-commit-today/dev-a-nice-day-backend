package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.domain.rds.image.UrlImage;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_blog",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_blog__uid", columnNames = {"uid"}),
                @UniqueConstraint(name = "uk_tech_blog__url", columnNames = {"url"})
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TechBlog extends BaseSoftDeleteEntity {

    @Column(name = "uid", length = 45, nullable = false)
    private String uid;

    @Enumerated(STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private TechBlogType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Nullable
    private UrlImage icon;

    @Builder
    public TechBlog(
            final String uid,
            final TechBlogType type,
            final String title,
            final String url,
            @Nullable final UrlImage icon
    ) {
        this.uid = uid;
        this.type = type;
        this.title = title;
        this.url = url;
        this.icon = icon;
    }
}
