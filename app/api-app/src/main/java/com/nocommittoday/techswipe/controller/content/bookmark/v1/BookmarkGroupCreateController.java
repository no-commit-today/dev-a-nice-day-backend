package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.request.BookmarkGroupCreateRequest;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkGroupCreateService;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkGroupCreateController {

    private final BookmarkGroupCreateService bookmarkGroupCreateService;

    public BookmarkGroupCreateController(BookmarkGroupCreateService bookmarkGroupCreateService) {
        this.bookmarkGroupCreateService = bookmarkGroupCreateService;
    }

    @PostMapping("/api/bookmark/v1/groups")
    public void create(ApiUser apiUser, @RequestBody @Validated BookmarkGroupCreateRequest request) {
        bookmarkGroupCreateService.create(apiUser, request.name());
    }
}
