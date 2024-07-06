package com.nocommittoday.techswipe.admin.controller;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TechContentSyncQueryRequest(
        @NotNull LocalDateTime from,
        @NotNull LocalDateTime to
) {
}
