package com.nocommittoday.techswipe.image.service;

import java.time.LocalDateTime;

public record ImageSyncQueryParam(
        LocalDateTime from,
        LocalDateTime to
) {
}
