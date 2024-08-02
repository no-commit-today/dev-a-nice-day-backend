package com.nocommittoday.techswipe.subscription.storage.mysql;


public class SubscriptionData {

    private FeedData feed;
    private ContentCrawlingData contentCrawling;
    private ListCrawlingListData listCrawlings;

    protected SubscriptionData() {
    }

    public SubscriptionData(FeedData feed, ContentCrawlingData contentCrawling, ListCrawlingListData listCrawlings) {
        this.feed = feed;
        this.contentCrawling = contentCrawling;
        this.listCrawlings = listCrawlings;
    }

    public ContentCrawlingData getContentCrawling() {
        return contentCrawling;
    }

    public FeedData getFeed() {
        return feed;
    }

    public ListCrawlingListData getListCrawlings() {
        return listCrawlings;
    }
}
