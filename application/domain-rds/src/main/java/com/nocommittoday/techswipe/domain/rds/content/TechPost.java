package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
                @UniqueConstraint(name = "uk_tech_post__link", columnNames = {"link"})
        },
        indexes = {
                @Index(name = "ix_tech_post__published_at", columnList = "published_at")
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TechPost extends BaseSoftDeleteEntity {

    @Column(name = "uid", length = 45, nullable = false)
    private String uid;

    @Column(name = "link", length = 1000, nullable = false)
    private String link;

    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", length = 100_000_000, nullable = false)
    private String content;

    @Lob
    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

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
            final String link,
            final String title,
            final String content,
            final String summary,
            final LocalDateTime publishedAt,
            @Nullable final List<TechKeyword> keywords,
            @Nullable final List<TechCategory> categories
    ) {
        this.uid = uid;
        this.link = link;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.publishedAt = publishedAt;
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
