package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.response.BookmarkGroupQueryResponse;
import com.nocommittoday.techswipe.controller.core.ListResponse;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupListQueryService;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkGroupListQueryController {

    private final BookmarkGroupListQueryService bookmarkGroupListQueryService;

    public BookmarkGroupListQueryController(BookmarkGroupListQueryService bookmarkGroupListQueryService) {
        this.bookmarkGroupListQueryService = bookmarkGroupListQueryService;
    }

    @GetMapping("/api/bookmark/v1/groups")
    public ListResponse<BookmarkGroupQueryResponse> getList(ApiUser apiUser) {
        return new ListResponse<>(
                bookmarkGroupListQueryService.getList(apiUser).stream()
                        .map(BookmarkGroupQueryResponse::from)
                        .toList()
        );
    }
}
