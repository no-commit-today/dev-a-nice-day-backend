package com.nocommittoday.techswipe.controller.bookmark.v1.request;

import com.nocommittoday.techswipe.domain.bookmark.BookmarkConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookmarkGroupCreateRequest(
        @NotBlank @Size(max = BookmarkConst.MAX_GROUP_NAME_LENGTH) String name
) {
}
