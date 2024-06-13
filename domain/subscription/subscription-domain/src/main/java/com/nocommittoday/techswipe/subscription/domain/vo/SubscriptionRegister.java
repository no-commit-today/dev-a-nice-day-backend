package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionRegisterFailureException;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public record SubscriptionRegister(
        @NonNull TechContentProvider.TechContentProviderId providerId,
        @NonNull SubscriptionType type,
        @NonNull SubscriptionInitType initType,
        @Nullable String rssUrl,
        @Nullable String atomUrl,
        @NonNull ContentCrawling contentCrawling,
        @NonNull List<ListCrawling> listCrawlings
) {

    public void validate() {
        if (type == SubscriptionType.RSS) {
            validateRss();
        }
        if (type == SubscriptionType.ATOM) {
            validateAtom();
        }
        if (type == SubscriptionType.LIST_CRAWLING || initType == SubscriptionInitType.LIST_CRAWLING) {
            validateListCrawling();
        }
    }

    private void validateRss() {
        if (rssUrl == null) {
            throw new SubscriptionRegisterFailureException("rssUrl이 필요합니다.");
        }
        validateContentCrawling();
    }

    private void validateAtom() {
        if (atomUrl == null) {
            throw new SubscriptionRegisterFailureException("atomUrl이 필요합니다.");
        }
        validateContentCrawling();
    }

    private void validateListCrawling() {
        if (listCrawlings.isEmpty()) {
            throw new SubscriptionRegisterFailureException("listCrawlings이 필요합니다.");
        }
        listCrawlings.forEach(listCrawling -> {
            if (listCrawling.url() == null) {
                throw new SubscriptionRegisterFailureException("listCrawling.url이 필요합니다.");
            }
            if (listCrawling.crawling() == null) {
                throw new SubscriptionRegisterFailureException("listCrawling.crawling이 필요합니다.");
            }
            if (CrawlingType.NONE == listCrawling.crawling().type()) {
                throw new SubscriptionRegisterFailureException("listCrawling.crawling.type이 필요합니다.");
            }
            validateCrawling(listCrawling.crawling());

            validateContentCrawling();
            if (CrawlingType.NONE == contentCrawling.title().type()
                    || CrawlingType.NONE == contentCrawling.date().type()
                    || CrawlingType.NONE == contentCrawling.content().type()
            ) {
                throw new SubscriptionRegisterFailureException("contentCrawling의 모든 값이 필요합니다.");
            }
        });
    }

    private void validateContentCrawling() {
        if (contentCrawling.title() == null
                || contentCrawling.date() == null
                || contentCrawling.content() == null
        ) {
            throw new SubscriptionRegisterFailureException("contentCrawling이 필요합니다.");
        }
        validateCrawling(contentCrawling.title());
        validateCrawling(contentCrawling.date());
        validateCrawling(contentCrawling.content());
    }

    private void validateCrawling(final Crawling crawling) {
        if (CrawlingType.INDEX == crawling.type()
                && (crawling.indexes() == null || crawling.indexes().isEmpty())) {
            throw new SubscriptionRegisterFailureException(CrawlingType.INDEX + "타입은 crawling.indexes가() 필수입니다.");
        }
        if (CrawlingType.SELECTOR == crawling.type()
                && (crawling.selector() == null || crawling.selector().isEmpty())) {
            throw new SubscriptionRegisterFailureException(CrawlingType.SELECTOR + "타입은 selector가 필수입니다.");
        }
    }
}
