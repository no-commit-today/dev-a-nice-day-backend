package com.nocommittoday.techswipe.subscription.infrastructure.mysql;

import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingIndexes;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionData {

    @Nullable
    private String rssUrl;
    @Nullable
    private String atomUrl;
    @Nullable
    private ContentCrawlingIndexes contentCrawlingIndexes;
    @Nullable
    private List<ListCrawling> listCrawlings;
}
