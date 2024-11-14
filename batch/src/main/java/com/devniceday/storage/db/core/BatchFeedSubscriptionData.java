package com.devniceday.storage.db.core;

public class BatchFeedSubscriptionData {

    private String url;

    private BatchSubscriptionContentScrappingData contentScrapping;

    public BatchFeedSubscriptionData(String url, BatchSubscriptionContentScrappingData contentScrapping) {
        this.url = url;
        this.contentScrapping = contentScrapping;
    }

    public String getUrl() {
        return url;
    }

    public BatchSubscriptionContentScrappingData getContentScrapping() {
        return contentScrapping;
    }
}
