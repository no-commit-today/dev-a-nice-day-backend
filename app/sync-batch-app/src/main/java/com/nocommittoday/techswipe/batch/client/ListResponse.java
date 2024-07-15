package com.nocommittoday.techswipe.batch.client;

import java.util.List;

public record ListResponse<T>(
        List<T> content
) {
}
