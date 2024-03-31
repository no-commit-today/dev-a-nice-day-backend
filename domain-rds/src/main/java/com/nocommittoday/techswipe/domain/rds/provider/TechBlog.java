package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.techswipe.domain.rds.core.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "tech_blog")
public class TechBlog extends BaseSoftDeleteEntity {

    @Enumerated(STRING)
    @Column(name = "name", length = 45, nullable = false)
    private TechBlogType type;

    @Enumerated(STRING)
    @Column(name = "feed_type", length = 45, nullable = false)
    private TechBlogFeedType feedType;

    @Column(name = "title", length = 105, nullable = false)
    private String title;

    @Column(name = "link", length = 1000, nullable = false)
    private String link;

    @Column(name = "feed_link", length = 1000, nullable = false)
    private String feedLink;

    public static TechBlogBuilder builder() {
        return new TechBlogBuilder();
    }

    protected TechBlog() {
    }

    public TechBlogType getType() {
        return type;
    }

    public TechBlogFeedType getFeedType() {
        return feedType;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getFeedLink() {
        return feedLink;
    }


    public static final class TechBlogBuilder {
        private TechBlogType type;
        private TechBlogFeedType feedType;
        private String title;
        private String link;
        private String feedLink;

        TechBlogBuilder() {
        }

        public TechBlogBuilder type(TechBlogType type) {
            this.type = type;
            return this;
        }

        public TechBlogBuilder feedType(TechBlogFeedType feedType) {
            this.feedType = feedType;
            return this;
        }

        public TechBlogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TechBlogBuilder link(String link) {
            this.link = link;
            return this;
        }

        public TechBlogBuilder feedLink(String feedLink) {
            this.feedLink = feedLink;
            return this;
        }

        public TechBlog build() {
            TechBlog techBlog = new TechBlog();
            techBlog.feedType = this.feedType;
            techBlog.link = this.link;
            techBlog.title = this.title;
            techBlog.type = this.type;
            techBlog.feedLink = this.feedLink;
            return techBlog;
        }
    }
}
