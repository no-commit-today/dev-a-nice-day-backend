package com.devniceday.storage.db.core;

public class BatchContentScrappingData {

    private BatchScrappingData title;
    private BatchScrappingData date;
    private BatchScrappingData content;
    private BatchImageScrappingData image;

    public BatchContentScrappingData(
            BatchScrappingData title,
            BatchScrappingData date,
            BatchScrappingData content,
            BatchImageScrappingData image
    ) {
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

    public BatchImageScrappingData getImage() {
        return image;
    }
}
