package com.nocommittoday.techswipe.domain.content;

public record TechContentQueryResult(
        TechContentQuery content,
        boolean bookmarked
) {
}
