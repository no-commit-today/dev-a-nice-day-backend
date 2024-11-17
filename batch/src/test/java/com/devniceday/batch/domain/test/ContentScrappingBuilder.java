package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.Scrapping;

import javax.annotation.Nullable;

public class ContentScrappingBuilder {

    @Nullable
    private Scrapping title;

    @Nullable
    private Scrapping date;

    @Nullable
    private Scrapping content;

    @Nullable
    private Scrapping image;

    public static ContentScrapping create() {
        return new ContentScrappingBuilder().build();
    }


    public ContentScrappingBuilder title(Scrapping title) {
        this.title = title;
        return this;
    }

    public ContentScrappingBuilder date(Scrapping date) {
        this.date = date;
        return this;
    }

    public ContentScrappingBuilder content(Scrapping content) {
        this.content = content;
        return this;
    }

    public ContentScrappingBuilder image(Scrapping image) {
        this.image = image;
        return this;
    }

    public ContentScrapping build() {
        fillRequiredFields();
        return new ContentScrapping(title, date, content, image);
    }

    private void fillRequiredFields() {
        if (title == null) {
            title = ScrappingBuilder.selector();
        }
        if (date == null) {
            date = ScrappingBuilder.selector();
        }
        if (content == null) {
            content = ScrappingBuilder.selector();
        }
        if (image == null) {
            image = ScrappingBuilder.openGraphImage();
        }
    }
}
