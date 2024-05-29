package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public record SubscriptionRegister(
        @NonNull SubscriptionType type,
        @Nullable String rssUrl,
        @Nullable String atomUrl,
        @Nullable ContentCrawlingIndexes contentCrawlingIndexes,
        @Nullable List<ListCrawling> listCrawlings
) {

    public void validate() {
        if (type == SubscriptionType.LIST_CRAWLING) {
            validateListCrawlings();
            validateContentCrawlingIndexes();
        } else if (type == SubscriptionType.RSS) {
            validateContentCrawling();
            if (rssUrl == null) {
                throw new IllegalArgumentException("rssUrl 은 필수입니다.");
            }
        } else if (type == SubscriptionType.ATOM) {
            validateContentCrawling();
            if (atomUrl == null) {
                throw new IllegalArgumentException("atomUrl 은 필수입니다.");
            }
        } else {
            throw new IllegalArgumentException("지원하지 않는 SubscriptionType 입니다. type: " + type);
        }
    }

    private void validateListCrawlings() {
        if (listCrawlings == null || listCrawlings.isEmpty()) {
            throw new IllegalArgumentException("listCrawlings 는 필수입니다.");
        }
        for (ListCrawling listCrawling : listCrawlings) {
            if (listCrawling == null) {
                throw new IllegalArgumentException("listCrawling 는 필수입니다.");
            }
        }
    }

    private void validateContentCrawlingIndexes() {
        if (contentCrawlingIndexes == null) {
            throw new IllegalArgumentException("postCrawlingSelectors 는 null 일 수 없습니다.");
        }
        // title 이 null 일 경우 html 의 title 가져옴
        if (contentCrawlingIndexes.date() == null) {
            throw new IllegalArgumentException("postCrawlingSelectors.date 는 null 일 수 없습니다.");
        }
        if (contentCrawlingIndexes.content() == null) {
            throw new IllegalArgumentException("postCrawlingSelectors.content 는 null 일 수 없습니다.");
        }
    }

    private void validateContentCrawling() {
        final boolean dateSelectorPresent = Optional.ofNullable(contentCrawlingIndexes)
                .map(ContentCrawlingIndexes::date)
                .isPresent();
        final boolean contentSelectorPresent = Optional.ofNullable(contentCrawlingIndexes)
                .map(ContentCrawlingIndexes::content)
                .isPresent();
        // title 이 null 일 경우 html 의 title 가져옴
        if (dateSelectorPresent) {
            throw new IllegalArgumentException("Date crawling selector 가 필요합니다.");
        }
        if (contentSelectorPresent) {
            throw new IllegalArgumentException("Content crawling selector 가 필요합니다.");
        }
    }
}
