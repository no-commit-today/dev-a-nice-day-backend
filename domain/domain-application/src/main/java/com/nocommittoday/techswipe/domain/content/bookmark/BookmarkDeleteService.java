package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

@Service
public class BookmarkDeleteService {

    private final BookmarkGroupReader bookmarkGroupReader;
    private final BookmarkRemover bookmarkRemover;

    public BookmarkDeleteService(BookmarkGroupReader bookmarkGroupReader, BookmarkRemover bookmarkRemover) {
        this.bookmarkGroupReader = bookmarkGroupReader;
        this.bookmarkRemover = bookmarkRemover;
    }

    public void delete(ApiUser apiUser, String groupName, TechContentId contentId) {
        BookmarkGroup group = bookmarkGroupReader.read(apiUser.getUserId(), groupName);
        bookmarkRemover.remove(group.getId(), contentId);
    }
}
