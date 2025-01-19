package com.devniceday.storage.db.core;

public class BatchWebListSubscriptionData {

    private BatchListScrappingData listScrapping;

    private BatchContentScrappingData contentScrapping;

    protected BatchWebListSubscriptionData() {
    }

    public BatchWebListSubscriptionData(BatchListScrappingData listScrapping, BatchContentScrappingData contentScrapping) {
        this.listScrapping = listScrapping;
        this.contentScrapping = contentScrapping;
    }

    public BatchListScrappingData getListScrapping() {
        return listScrapping;
    }

    public BatchContentScrappingData getContentScrapping() {
        return contentScrapping;
    }
}
