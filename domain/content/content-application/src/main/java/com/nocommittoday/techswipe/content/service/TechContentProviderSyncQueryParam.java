package com.nocommittoday.techswipe.content.service;

import java.time.LocalDateTime;

public record TechContentProviderSyncQueryParam(
        LocalDateTime from,
        LocalDateTime to
) {
}
