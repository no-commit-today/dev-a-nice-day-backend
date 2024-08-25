package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.domain.subscription.ContentScrapping;

public class ContentScrappingData {

    private ScrappingData title;
    private ScrappingData date;
    private ScrappingData content;
    private ScrappingData image;

    protected ContentScrappingData() {
    }

    public ContentScrappingData(ScrappingData title, ScrappingData date, ScrappingData content, ScrappingData image) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public ContentScrapping toDomain() {
        return new ContentScrapping(
                title.toDomain(), date.toDomain(), content.toDomain(), image.toDomain()
        );
    }

    public ScrappingData getTitle() {
        return title;
    }

    public ScrappingData getDate() {
        return date;
    }

    public ScrappingData getContent() {
        return content;
    }

    public ScrappingData getImage() {
        return image;
    }
}
