package com.nocommittoday.techswipe.domain.rds.subscription;

import lombok.Builder;

@Builder
public record RssTechBlogSubscription(
        String url,
        PostCrawlingIndexes postCrawlingIndexes,
        PostCrawlingNeeds postCrawlingNeeds
) {
}
