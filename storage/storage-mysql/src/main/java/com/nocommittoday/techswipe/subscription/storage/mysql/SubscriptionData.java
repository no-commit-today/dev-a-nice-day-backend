package com.nocommittoday.techswipe.subscription.storage.mysql;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionData {

    private FeedData feed;
    private ContentCrawlingData contentCrawling;
    private ListCrawlingListData listCrawlings;
}
