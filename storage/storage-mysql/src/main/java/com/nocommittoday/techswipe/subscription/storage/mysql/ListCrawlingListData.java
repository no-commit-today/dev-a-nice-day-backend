package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.ListCrawling;

import java.util.List;

public class ListCrawlingListData {

    private List<ListCrawlingData> content;

    protected ListCrawlingListData() {
    }

    public ListCrawlingListData(List<ListCrawlingData> content) {
        this.content = content;
    }

    public static ListCrawlingListData from(List<ListCrawling> listCrawling) {
        return new ListCrawlingListData(
                listCrawling.stream()
                        .map(ListCrawlingData::from)
                        .toList()
        );
    }

    public List<ListCrawling> toDomain() {
        return content.stream()
                .map(ListCrawlingData::toDomain)
                .toList();
    }

    public List<ListCrawlingData> getContent() {
        return content;
    }
}
