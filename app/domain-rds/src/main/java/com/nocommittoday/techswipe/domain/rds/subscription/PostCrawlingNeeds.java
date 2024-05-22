package com.nocommittoday.techswipe.domain.rds.subscription;

public record PostCrawlingNeeds(
        boolean title,
        boolean date,
        boolean content
) {
}
