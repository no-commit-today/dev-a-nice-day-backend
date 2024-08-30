package com.nocommittoday.techswipe.domain.content;

import java.util.List;

public record TechContentListQueryResult(
        List<TechContentQuery> content
) {
}
