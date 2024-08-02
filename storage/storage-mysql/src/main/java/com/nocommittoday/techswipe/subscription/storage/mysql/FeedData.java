package com.nocommittoday.techswipe.subscription.storage.mysql;


import javax.annotation.Nullable;

public class FeedData {

    @Nullable
    private String url;

    protected FeedData() {
    }

    public FeedData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
