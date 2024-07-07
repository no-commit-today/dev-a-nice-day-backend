package com.nocommittoday.techswipe.admin.controller;

import lombok.NonNull;

import java.time.LocalDateTime;

public record ImageSyncQueryRequest(
        @NonNull LocalDateTime from,
        @NonNull LocalDateTime to
) {
}
