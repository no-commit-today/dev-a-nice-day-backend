package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

@Service
public class BookmarkGroupCreateService {

    private final BookmarkGroupAppender bookmarkGroupAppender;

    public BookmarkGroupCreateService(BookmarkGroupAppender bookmarkGroupAppender) {
        this.bookmarkGroupAppender = bookmarkGroupAppender;
    }

    public void create(ApiUser user, String name) {
        bookmarkGroupAppender.append(user.getUserId(), name);
    }
}
