package com.nocommittoday.techswipe.storage.mysql.subscription;

public class FeedSubscriptionData {

    private String url;

    private ContentScrappingData contentScrapping;

    protected FeedSubscriptionData() {
    }

    public FeedSubscriptionData(String url, ContentScrappingData contentScrapping) {
        this.url = url;
        this.contentScrapping = contentScrapping;
    }

    public String getUrl() {
        return url;
    }

    public ContentScrappingData getContentScrapping() {
        return contentScrapping;
    }
}
