package com.nocommittoday.techswipe.controller.content.bookmark.v1.request;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookmarkGroupCreateRequest(
        @NotBlank @Size(max = BookmarkConst.MAX_GROUP_NAME_LENGTH) String name
) {
}
