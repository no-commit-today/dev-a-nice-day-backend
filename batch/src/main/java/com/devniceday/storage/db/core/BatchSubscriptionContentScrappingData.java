package com.devniceday.storage.db.core;

public class BatchSubscriptionContentScrappingData {

    private BatchScrappingData title;
    private BatchScrappingData date;
    private BatchScrappingData content;
    private BatchScrappingData image;

    public BatchSubscriptionContentScrappingData(
            BatchScrappingData title, BatchScrappingData date, BatchScrappingData content, BatchScrappingData image) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public BatchScrappingData getTitle() {
        return title;
    }

    public BatchScrappingData getDate() {
        return date;
    }

    public BatchScrappingData getContent() {
        return content;
    }

    public BatchScrappingData getImage() {
        return image;
    }
}
