package com.nocommittoday.techswipe.admin.controller.request;

import jakarta.validation.constraints.NotBlank;

public record AdminCollectionSummaryRegisterRequest(
    @NotBlank String summary
) {
}
