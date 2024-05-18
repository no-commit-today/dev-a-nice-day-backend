package com.nocommittoday.techswipe.domain.rds.subscription;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.List;
import java.util.Optional;


@Builder
public record TechBlogSubscription(
        long techBlogId,
        TechBlogSubscriptionType type,
        @Nullable
        String rssUrl,
        @Nullable
        String atomUrl,
        @Nullable
        PostCrawlingSelectors postCrawlingSelectors,
        @Nullable
        PostCrawlingNeeds postCrawlingNeeds,
        @Nullable
        List<ListCrawling> listCrawlings

) {

    public TechBlogSubscription {
        if (type == TechBlogSubscriptionType.LIST_CRAWLING) {
            validateListCrawlings(listCrawlings);
            validatePostCrawlingSelectors(postCrawlingSelectors);
        } else if (type == TechBlogSubscriptionType.RSS) {
            validatePostCrawling(postCrawlingNeeds, postCrawlingSelectors);
            if (rssUrl == null) {
                throw new IllegalArgumentException("rssUrl 은 필수입니다.");
            }
        } else if (type == TechBlogSubscriptionType.ATOM) {
            validatePostCrawling(postCrawlingNeeds, postCrawlingSelectors);
            if (atomUrl == null) {
                throw new IllegalArgumentException("atomUrl 은 필수입니다.");
            }
        }
    }

    public RssTechBlogSubscription toRss() {
        return RssTechBlogSubscription.builder()
                .url(rssUrl)
                .postCrawlingSelectors(postCrawlingSelectors)
                .postCrawlingNeeds(postCrawlingNeeds)
                .build();
    }

    public AtomTechBlogSubscription toAtom() {
        return AtomTechBlogSubscription.builder()
                .url(atomUrl)
                .postCrawlingSelectors(postCrawlingSelectors)
                .postCrawlingNeeds(postCrawlingNeeds)
                .build();
    }

    public List<ListCrawlingTechBlogSubscription> toListCrawling() {
        return listCrawlings.stream()
                .map(listCrawling -> ListCrawlingTechBlogSubscription.builder()
                        .postCrawlingSelectors(postCrawlingSelectors)
                        .listCrawling(listCrawling)
                        .build()
                ).toList();
    }

    private static void validateListCrawlings(final List<ListCrawling> listCrawlings) {
        if (listCrawlings == null || listCrawlings.isEmpty()) {
            throw new IllegalArgumentException("listCrawlings 는 필수입니다.");
        }
        for (ListCrawling listCrawling : listCrawlings) {
            if (listCrawling == null) {
                throw new IllegalArgumentException("listCrawling 는 필수입니다.");
            }
            if (listCrawling.url() == null) {
                throw new IllegalArgumentException("listCrawling.url 는 필수입니다.");
            }
            if (listCrawling.selector() == null) {
                throw new IllegalArgumentException("listCrawling.listSelector 는 필수입니다.");
            }
        }
    }

    private static void validatePostCrawlingSelectors(final PostCrawlingSelectors postCrawlingSelectors) {
        if (postCrawlingSelectors == null) {
            throw new IllegalArgumentException("postCrawlingSelectors 는 null 일 수 없습니다.");
        }
        // title 이 null 일 경우 html 의 title 가져옴
        if (postCrawlingSelectors.date() == null) {
            throw new IllegalArgumentException("postCrawlingSelectors.date 는 null 일 수 없습니다.");
        }
        if (postCrawlingSelectors.content() == null) {
            throw new IllegalArgumentException("postCrawlingSelectors.content 는 null 일 수 없습니다.");
        }
    }

    private static void validatePostCrawling(
            final PostCrawlingNeeds postCrawlingNeeds,
            final PostCrawlingSelectors postCrawlingSelectors
    ) {
        if (postCrawlingNeeds == null) {
            throw new IllegalArgumentException("postCrawlingNeeds 는 필수입니다.");
        }
        final boolean dateSelectorPresent = Optional.ofNullable(postCrawlingSelectors)
                .map(PostCrawlingSelectors::date)
                .isPresent();
        final boolean contentSelectorPresent = Optional.ofNullable(postCrawlingSelectors)
                .map(PostCrawlingSelectors::content)
                .isPresent();
        // title 이 null 일 경우 html 의 title 가져옴
        if (postCrawlingNeeds.date() && dateSelectorPresent) {
            throw new IllegalArgumentException("Date crawling selector 가 필요합니다.");
        }
        if (postCrawlingNeeds.content() && contentSelectorPresent) {
            throw new IllegalArgumentException("Content crawling selector 가 필요합니다.");
        }
    }
}
