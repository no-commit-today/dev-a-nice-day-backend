package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListCrawlingListData {

    private List<ListCrawlingData> content;

    public static ListCrawlingListData from(final List<ListCrawling> listCrawling) {
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
}
