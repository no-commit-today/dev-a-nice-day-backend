package com.nocommittoday.techswipe.domain.subscription;

public class ContentScrapping {

    private final Scrapping title;
    private final Scrapping date;
    private final Scrapping content;
    private final Scrapping image;

    public ContentScrapping(Scrapping title, Scrapping date, Scrapping content, Scrapping image) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public Scrapping getTitle() {
        return title;
    }

    public Scrapping getDate() {
        return date;
    }

    public Scrapping getContent() {
        return content;
    }

    public Scrapping getImage() {
        return image;
    }
}
