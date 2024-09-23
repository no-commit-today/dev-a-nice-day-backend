package com.nocommittoday.techswipe.controller.bookmark.v1;

import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroupDeleteService;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkGroupDeleteController {

    private final BookmarkGroupDeleteService bookmarkGroupDeleteService;

    public BookmarkGroupDeleteController(BookmarkGroupDeleteService bookmarkGroupDeleteService) {
        this.bookmarkGroupDeleteService = bookmarkGroupDeleteService;
    }

    @DeleteMapping("/api/bookmark/v1/groups/{groupName}")
    public void delete(ApiUser apiUser, @PathVariable String groupName) {
        bookmarkGroupDeleteService.delete(apiUser, groupName);
    }
}
