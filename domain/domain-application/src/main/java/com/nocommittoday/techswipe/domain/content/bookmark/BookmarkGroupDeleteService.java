package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

@Service
public class BookmarkGroupDeleteService {

    private final BookmarkGroupRemover bookmarkGroupRemover;

    public BookmarkGroupDeleteService(BookmarkGroupRemover bookmarkGroupRemover) {
        this.bookmarkGroupRemover = bookmarkGroupRemover;
    }

    public void delete(ApiUser apiUser, String groupName) {
        bookmarkGroupRemover.remove(apiUser.getUserId(), groupName);
    }
}
