package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

@Service
public class BookmarkCreateService {

    private final BookmarkGroupReader bookmarkGroupReader;
    private final BookmarkAppender bookmarkAppender;

    public BookmarkCreateService(BookmarkGroupReader bookmarkGroupReader, BookmarkAppender bookmarkAppender) {
        this.bookmarkGroupReader = bookmarkGroupReader;
        this.bookmarkAppender = bookmarkAppender;
    }

    public void create(ApiUser apiUser, String groupName, TechContentId contentId) {
        BookmarkGroup group = bookmarkGroupReader.read(apiUser.getUserId(), groupName);
        bookmarkAppender.append(group.getId(), contentId);
    }
}
