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
        PostCrawlingIndexes postCrawlingIndexes,
        @Nullable
        PostCrawlingNeeds postCrawlingNeeds,
        @Nullable
        List<ListCrawling> listCrawlings

) {

    public TechBlogSubscription {
        if (type == TechBlogSubscriptionType.LIST_CRAWLING) {
            validateListCrawlings(listCrawlings);
            validatePostCrawlingSelectors(postCrawlingIndexes);
        } else if (type == TechBlogSubscriptionType.RSS) {
            validatePostCrawling(postCrawlingNeeds, postCrawlingIndexes);
            if (rssUrl == null) {
                throw new IllegalArgumentException("rssUrl 은 필수입니다.");
            }
        } else if (type == TechBlogSubscriptionType.ATOM) {
            validatePostCrawling(postCrawlingNeeds, postCrawlingIndexes);
            if (atomUrl == null) {
                throw new IllegalArgumentException("atomUrl 은 필수입니다.");
            }
        }
    }

    public RssTechBlogSubscription toRss() {
        return RssTechBlogSubscription.builder()
                .url(rssUrl)
                .postCrawlingIndexes(postCrawlingIndexes)
                .postCrawlingNeeds(postCrawlingNeeds)
                .build();
    }

    public AtomTechBlogSubscription toAtom() {
        return AtomTechBlogSubscription.builder()
                .url(atomUrl)
                .postCrawlingIndexes(postCrawlingIndexes)
                .postCrawlingNeeds(postCrawlingNeeds)
                .build();
    }

    public List<ListCrawlingTechBlogSubscription> toListCrawling() {
        return listCrawlings.stream()
                .map(listCrawling -> ListCrawlingTechBlogSubscription.builder()
                        .postCrawlingIndexes(postCrawlingIndexes)
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
            if (listCrawling.indexes() == null) {
                throw new IllegalArgumentException("listCrawling.listSelector 는 필수입니다.");
            }
        }
    }

    private static void validatePostCrawlingSelectors(final PostCrawlingIndexes postCrawlingIndexes) {
        if (postCrawlingIndexes == null) {
            throw new IllegalArgumentException("postCrawlingSelectors 는 null 일 수 없습니다.");
        }
        // title 이 null 일 경우 html 의 title 가져옴
        if (postCrawlingIndexes.date() == null) {
            throw new IllegalArgumentException("postCrawlingSelectors.date 는 null 일 수 없습니다.");
        }
        if (postCrawlingIndexes.content() == null) {
            throw new IllegalArgumentException("postCrawlingSelectors.content 는 null 일 수 없습니다.");
        }
    }

    private static void validatePostCrawling(
            final PostCrawlingNeeds postCrawlingNeeds,
            final PostCrawlingIndexes postCrawlingIndexes
    ) {
        if (postCrawlingNeeds == null) {
            throw new IllegalArgumentException("postCrawlingNeeds 는 필수입니다.");
        }
        final boolean dateSelectorPresent = Optional.ofNullable(postCrawlingIndexes)
                .map(PostCrawlingIndexes::date)
                .isPresent();
        final boolean contentSelectorPresent = Optional.ofNullable(postCrawlingIndexes)
                .map(PostCrawlingIndexes::content)
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
