package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkDeleteService;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkDeleteController {

    private final BookmarkDeleteService bookmarkDeleteService;

    public BookmarkDeleteController(BookmarkDeleteService bookmarkDeleteService) {
        this.bookmarkDeleteService = bookmarkDeleteService;
    }

    @DeleteMapping("/api/bookmark/v1/groups/{groupName}/contents/{contentId}")
    public void delete(ApiUser apiUser, @PathVariable String groupName, @PathVariable long contentId) {
        bookmarkDeleteService.delete(apiUser, groupName, new TechContentId(contentId));
    }
}
