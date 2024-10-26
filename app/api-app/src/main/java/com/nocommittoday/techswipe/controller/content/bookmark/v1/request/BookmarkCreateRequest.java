package com.nocommittoday.techswipe.controller.content.bookmark.v1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookmarkCreateRequest(
        @NotBlank String groupName,
        @NotNull @Positive Long contentId
) {
}
