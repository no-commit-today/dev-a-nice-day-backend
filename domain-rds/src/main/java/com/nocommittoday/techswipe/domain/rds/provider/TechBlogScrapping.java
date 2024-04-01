package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.techswipe.domain.rds.core.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tech_blog_scrapping")
public class TechBlogScrapping extends BaseIdEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "published_date")
    private String publishedDate;

    protected TechBlogScrapping() {
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPublishedDate() {
        return publishedDate;
    }


}
