package com.nocommittoday.techswipe.controller.content.bookmark.v1;

import com.nocommittoday.techswipe.controller.content.bookmark.v1.request.BookmarkCreateRequest;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkCreateService;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookmarkCreateController {

    private final BookmarkCreateService bookmarkCreateService;

    public BookmarkCreateController(BookmarkCreateService bookmarkCreateService) {
        this.bookmarkCreateService = bookmarkCreateService;
    }

    @PostMapping("/api/bookmark/v1/bookmarks")
    public void create(ApiUser apiUser, @RequestBody @Validated BookmarkCreateRequest request) {
        bookmarkCreateService.create(apiUser, request.groupName(), new TechContentId(request.contentId()));
    }

    @PutMapping("/api/bookmark/v1/groups/{groupName}/contents/{contentId}")
    public void create(ApiUser apiUser, @PathVariable String groupName, @PathVariable Long contentId) {
        bookmarkCreateService.create(apiUser, groupName, new TechContentId(contentId));
    }
}
