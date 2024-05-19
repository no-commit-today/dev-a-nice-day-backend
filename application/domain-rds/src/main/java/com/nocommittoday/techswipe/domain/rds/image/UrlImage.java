package com.nocommittoday.techswipe.domain.rds.image;

import com.nocommittoday.techswipe.domain.rds.core.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_image")
public class UrlImage extends BaseTimeEntity {

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @Column(name = "original_url", length = 1000, nullable = false)
    private String originalUrl;

    @Column(name = "stored_name", length = 1000, nullable = false)
    private String storedName;

    protected UrlImage() {
    }

    public static TechBlogImageBuilder builder() {
        return new TechBlogImageBuilder();
    }

    public String getUrl() {
        return url;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getStoredName() {
        return storedName;
    }


    public static final class TechBlogImageBuilder {
        private String url;
        private String originalUrl;
        private String storedName;

        private TechBlogImageBuilder() {
        }

        public TechBlogImageBuilder url(final String url) {
            this.url = url;
            return this;
        }

        public TechBlogImageBuilder originalUrl(final String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public TechBlogImageBuilder storedName(final String storedName) {
            this.storedName = storedName;
            return this;
        }

        public UrlImage build() {
            UrlImage urlImage = new UrlImage();
            urlImage.url = this.url;
            urlImage.originalUrl = this.originalUrl;
            urlImage.storedName = this.storedName;
            return urlImage;
        }
    }
}
