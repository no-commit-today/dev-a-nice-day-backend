package com.nocommittoday.techswipe.storage.mysql.subscription;

public class ListScrappingSubscriptionData {

    private ListScrappingData listScrapping;

    private ContentScrappingData contentScrapping;

    protected ListScrappingSubscriptionData() {
    }

    public ListScrappingSubscriptionData(ListScrappingData listScrapping, ContentScrappingData contentScrapping) {
        this.listScrapping = listScrapping;
        this.contentScrapping = contentScrapping;
    }

    public ListScrappingData getListScrapping() {
        return listScrapping;
    }

    public ContentScrappingData getContentScrapping() {
        return contentScrapping;
    }
}
