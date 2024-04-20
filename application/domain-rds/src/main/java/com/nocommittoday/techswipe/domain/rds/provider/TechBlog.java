package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "tech_blog")
public class TechBlog extends BaseSoftDeleteEntity {

    @Enumerated(STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private TechBlogType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @Column(name = "icon_url", length = 1000)
    private String iconUrl;


    public static TechBlogBuilder builder() {
        return new TechBlogBuilder();
    }

    protected TechBlog() {
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

    public String getIconUrl() {
        return iconUrl;
    }

    public static final class TechBlogBuilder {
        private TechBlogType type;
        private String title;
        private String url;
        private String iconUrl;

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

        public TechBlogBuilder iconUrl(@Nullable final String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public TechBlog build() {
            TechBlog techBlog = new TechBlog();
            techBlog.type = this.type;
            techBlog.title = this.title;
            techBlog.url = this.url;
            techBlog.iconUrl = this.iconUrl;
            return techBlog;
        }
    }
}
