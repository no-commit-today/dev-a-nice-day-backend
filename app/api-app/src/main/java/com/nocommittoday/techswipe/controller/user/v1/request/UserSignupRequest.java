package com.nocommittoday.techswipe.controller.user.v1.request;

import jakarta.validation.constraints.NotBlank;

public record UserSignupRequest(
        @NotBlank String accessToken
) {
}
