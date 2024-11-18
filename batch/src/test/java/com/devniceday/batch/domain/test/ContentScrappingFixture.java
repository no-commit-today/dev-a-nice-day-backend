package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.ImageScrapping;
import com.devniceday.batch.domain.Scrapping;

import javax.annotation.Nullable;

public class ContentScrappingFixture {

    @Nullable
    private Scrapping title;

    @Nullable
    private Scrapping date;

    @Nullable
    private Scrapping content;

    @Nullable
    private ImageScrapping image;

    public static ContentScrapping create() {
        return new ContentScrappingFixture().build();
    }


    public ContentScrappingFixture title(Scrapping title) {
        this.title = title;
        return this;
    }

    public ContentScrappingFixture date(Scrapping date) {
        this.date = date;
        return this;
    }

    public ContentScrappingFixture content(Scrapping content) {
        this.content = content;
        return this;
    }

    public ContentScrappingFixture image(ImageScrapping image) {
        this.image = image;
        return this;
    }

    public ContentScrapping build() {
        fillRequiredFields();
        return new ContentScrapping(title, date, content, image);
    }

    private void fillRequiredFields() {
        if (title == null) {
            title = ScrappingFixture.selector();
        }
        if (date == null) {
            date = ScrappingFixture.selector();
        }
        if (content == null) {
            content = ScrappingFixture.selector();
        }
        if (image == null) {
            image = ImageScrappingFixture.create();
        }
    }
}
