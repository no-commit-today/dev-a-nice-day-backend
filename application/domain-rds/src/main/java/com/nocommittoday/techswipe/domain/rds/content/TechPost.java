package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.domain.rds.image.UrlImage;
import com.nocommittoday.techswipe.domain.rds.provider.TechBlog;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_post",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_post__uid", columnNames = {"uid"}),
                @UniqueConstraint(name = "uk_tech_post__url", columnNames = {"url"})
        },
        indexes = {
                @Index(
                        name = "ix_tech_post__tech_blog_id_published_date",
                        columnList = "tech_blog_id, published_date desc"
                ),
                @Index(name = "ix_tech_post__published_date", columnList = "published_date desc")
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TechPost extends BaseSoftDeleteEntity {

    @Column(name = "uid", length = 45, nullable = false)
    private String uid;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Nullable
    private UrlImage image;

    @Lob
    @Column(name = "content", length = 100_000_000, nullable = false)
    private String content;

    @Lob
    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "tech_blog_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechBlog techBlog;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TechPostKeyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TechPostCategory> categories = new ArrayList<>();

    public List<TechPostKeyword> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    public List<TechPostCategory> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    @Builder
    public TechPost(
            final String uid,
            final String url,
            final String title,
            @Nullable final UrlImage image,
            final String content,
            final String summary,
            final LocalDate publishedDate,
            final TechBlog techBlog,
            @Nullable final List<TechKeyword> keywords,
            @Nullable final List<TechCategory> categories
    ) {
        this.uid = uid;
        this.url = url;
        this.title = title;
        this.image = image;
        this.content = content;
        this.summary = summary;
        this.publishedDate = publishedDate;
        this.techBlog = techBlog;
        this.keywords = Optional.ofNullable(keywords)
                .map(item -> item.stream()
                        .map(keyword -> new TechPostKeyword(this, keyword))
                        .toList()
                ).orElse(List.of());
        this.categories = Optional.ofNullable(categories)
                .map(item -> item.stream()
                        .map(category -> new TechPostCategory(this, category))
                        .toList()
                ).orElse(List.of());
    }
}
