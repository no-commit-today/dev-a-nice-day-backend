package com.nocommittoday.techswipe.admin.controller;

import jakarta.validation.constraints.NotBlank;

public record CollectionSummaryRegisterRequest(
    @NotBlank String summary
) {
}
