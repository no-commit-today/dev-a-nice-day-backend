package com.nocommittoday.techswipe.domain.rds.subscription;

import lombok.Builder;

@Builder
public record AtomTechBlogSubscription(
        String url,
        PostCrawlingIndexes postCrawlingIndexes,
        PostCrawlingNeeds postCrawlingNeeds
) {
}
