package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.domain.rds.image.UrlImage;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(
        name = "tech_blog",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_blog__uid", columnNames = {"uid"})
        }
)
public class TechBlog extends BaseSoftDeleteEntity {

    @Column(name = "uid", length = 45, nullable = false)
    private String uid;

    @Enumerated(STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private TechBlogType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    private UrlImage icon;

    public static TechBlogBuilder builder() {
        return new TechBlogBuilder();
    }

    protected TechBlog() {
    }

    public String getUid() {
        return uid;
    }

    public TechBlogType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public UrlImage getIcon() {
        return icon;
    }

    public static final class TechBlogBuilder {
        private TechBlogType type;
        private String title;
        private String url;
        private UrlImage icon;

        private TechBlogBuilder() {
        }

        public TechBlogBuilder type(final TechBlogType type) {
            this.type = type;
            return this;
        }

        public TechBlogBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public TechBlogBuilder url(final String url) {
            this.url = url;
            return this;
        }

        public TechBlogBuilder icon(@Nullable final UrlImage icon) {
            this.icon = icon;
            return this;
        }

        public TechBlog build() {
            TechBlog techBlog = new TechBlog();
            techBlog.uid = UUID.randomUUID().toString();
            techBlog.type = this.type;
            techBlog.title = this.title;
            techBlog.url = this.url;
            techBlog.icon = this.icon;
            return techBlog;
        }
    }
}
