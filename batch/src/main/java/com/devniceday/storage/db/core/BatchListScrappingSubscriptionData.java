package com.devniceday.storage.db.core;

public class BatchListScrappingSubscriptionData {

    private BatchSubscriptionListScrappingData listScrapping;

    private BatchSubscriptionContentScrappingData contentScrapping;

    public BatchListScrappingSubscriptionData(BatchSubscriptionListScrappingData listScrapping, BatchSubscriptionContentScrappingData contentScrapping) {
        this.listScrapping = listScrapping;
        this.contentScrapping = contentScrapping;
    }

    public BatchSubscriptionListScrappingData getListScrapping() {
        return listScrapping;
    }

    public BatchSubscriptionContentScrappingData getContentScrapping() {
        return contentScrapping;
    }
}
