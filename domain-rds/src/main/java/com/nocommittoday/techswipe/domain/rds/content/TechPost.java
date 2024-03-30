package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
public class TechPost extends BaseSoftDeleteEntity {

    @Column(name = "uid", length = 45, nullable = false)
    private String uid;

    @Column(name = "link", length = 1000, nullable = false)
    private String link;

    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Lob
    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @OneToMany(
            mappedBy = "post",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<TechPostKeyword> keywords;

    @OneToMany(
            mappedBy = "post",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<TechPostCategory> categories;

    public static TechPostBuilder builder() {
        return new TechPostBuilder();
    }

    protected TechPost() {
    }

    public String getUid() {
        return uid;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public List<TechPostKeyword> getKeywords() {
        return keywords;
    }

    public List<TechPostCategory> getCategories() {
        return categories;
    }

    public static final class TechPostBuilder {

        private String link;
        private String title;
        private String content;
        private String summary;
        private LocalDateTime publishedAt;
        private List<TechKeyword> keywords = List.of();
        private List<TechCategory> categories = List.of();

        public TechPostBuilder() {
        }

        public TechPostBuilder link(final String link) {
            this.link = link;
            return this;
        }

        public TechPostBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public TechPostBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public TechPostBuilder summary(final String summary) {
            this.summary = summary;
            return this;
        }

        public TechPostBuilder publishedAt(final LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public TechPostBuilder keywords(final List<TechKeyword> keywords) {
            if (keywords == null) {
                this.keywords = List.of();
                return this;
            }
            this.keywords = new ArrayList<>(keywords);
            return this;
        }

        public TechPostBuilder categories(final List<TechCategory> categories) {
            if (categories == null) {
                this.categories = List.of();
                return this;
            }
            this.categories = new ArrayList<>(categories);
            return this;
        }

        public TechPost build() {
            final TechPost techPost = new TechPost();
            techPost.uid = UUID.randomUUID().toString();
            techPost.link = this.link;
            techPost.title = this.title;
            techPost.content = this.content;
            techPost.summary = this.summary;
            techPost.publishedAt = this.publishedAt;
            techPost.keywords = this.keywords.stream()
                    .map(keyword -> new TechPostKeyword(techPost, keyword))
                    .toList();
            techPost.categories = this.categories.stream()
                    .map(category -> new TechPostCategory(techPost, category))
                    .toList();
            return techPost;
        }
    }
}
