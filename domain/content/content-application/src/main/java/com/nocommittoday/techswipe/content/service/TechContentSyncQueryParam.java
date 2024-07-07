package com.nocommittoday.techswipe.content.service;

import java.time.LocalDateTime;

public record TechContentSyncQueryParam(
        LocalDateTime from,
        LocalDateTime to
) {
}
