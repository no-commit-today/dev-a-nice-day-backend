package com.devniceday.storage.db.core;

public class BatchFeedSubscriptionData {

    private String url;

    private BatchContentScrappingData contentScrapping;

    protected BatchFeedSubscriptionData() {
    }

    public BatchFeedSubscriptionData(String url, BatchContentScrappingData contentScrapping) {
        this.url = url;
        this.contentScrapping = contentScrapping;
    }

    public String getUrl() {
        return url;
    }

    public BatchContentScrappingData getContentScrapping() {
        return contentScrapping;
    }
}
