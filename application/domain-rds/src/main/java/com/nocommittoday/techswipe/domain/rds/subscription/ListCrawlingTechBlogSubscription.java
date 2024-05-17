package com.nocommittoday.techswipe.domain.rds.subscription;

import lombok.Builder;

@Builder
public record ListCrawlingTechBlogSubscription(
        PostCrawlingSelectors postCrawlingSelectors,
        ListCrawling listCrawling
) {
}
