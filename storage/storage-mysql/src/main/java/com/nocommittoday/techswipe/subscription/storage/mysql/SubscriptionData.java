package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling;
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
    private ContentCrawling contentCrawling;
    private List<ListCrawling> listCrawlings;
}
