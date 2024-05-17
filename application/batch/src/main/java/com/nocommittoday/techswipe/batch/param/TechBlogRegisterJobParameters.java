package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.domain.rds.provider.TechBlogType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class TechBlogRegisterJobParameters {

    private TechBlogType type;

    @Nullable
    private String title;

    private String url;

    @Value("#{jobParameters['techBlog.type']}")
    public void setType(final TechBlogType type) {
        this.type = type;
    }

    @Value("#{jobParameters['techBlog.title']}")
    public void setTitle(@Nullable @Size(max = 200) final String title) {
        this.title = title;
    }

    @Value("#{jobParameters['techBlog.url']}")
    public void setUrl(@URL final String url) {
        this.url = url;
    }
}
