package com.nocommittoday.techswipe.controller.content.v1.request;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserCategorySaveRequest(
        @NotNull List<TechCategory> categories
) {
}
