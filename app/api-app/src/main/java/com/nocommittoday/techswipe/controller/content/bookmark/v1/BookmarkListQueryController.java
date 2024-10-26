package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.response.BookmarkListQueryResponse;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkListQueryService;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkListQueryController {

    private final BookmarkListQueryService bookmarkListQueryService;

    public BookmarkListQueryController(BookmarkListQueryService bookmarkListQueryService) {
        this.bookmarkListQueryService = bookmarkListQueryService;
    }

    @GetMapping("/api/bookmark/v1/bookmarks")
    public BookmarkListQueryResponse getList(ApiUser apiUser, @RequestParam(required = false) String groupName) {
        return BookmarkListQueryResponse.from(bookmarkListQueryService.getList(apiUser, groupName));
    }
}
