package com.nocommittoday.techswipe.domain.rds.subscription;

import jakarta.annotation.Nullable;

import java.util.List;


public record TechBlogSubscription(
        long techBlogId,
        TechBlogSubscriptionType type,
        String rssUrl,
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
            validateListCrawlings();
            validatePostCrawlingSelectors();
        } else if (type == TechBlogSubscriptionType.RSS) {
            validatePostCrawling();
            if (rssUrl == null) {
                throw new IllegalArgumentException("rssUrl 은 필수입니다.");
            }
        } else if (type == TechBlogSubscriptionType.ATOM) {
            validatePostCrawling();
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

    private void validateListCrawlings() {
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

    private void validatePostCrawlingSelectors() {
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

    private void validatePostCrawling() {
        if (postCrawlingSelectors == null) {
            throw new IllegalArgumentException("postCrawlingSelectors 는 필수입니다.");
        }
        if (postCrawlingNeeds == null) {
            throw new IllegalArgumentException("postCrawlingNeeds 는 필수입니다.");
        }
        // title 이 null 일 경우 html 의 title 가져옴
        if (postCrawlingNeeds.date() && postCrawlingSelectors.date() == null) {
            throw new IllegalArgumentException("Date crawling selector 가 필요합니다.");
        }
        if (postCrawlingNeeds.content() && postCrawlingSelectors.content() == null) {
            throw new IllegalArgumentException("Content crawling selector 가 필요합니다.");
        }
    }
}
