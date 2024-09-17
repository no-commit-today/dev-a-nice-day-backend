package com.nocommittoday.techswipe.domain.bookmark.exception;

import com.nocommittoday.techswipe.domain.bookmark.BookmarkGroupId;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

import java.util.Map;

public class BookmarkAlreadyExistsException extends AbstractDomainException {

    public BookmarkAlreadyExistsException(BookmarkGroupId groupId, TechContentId contentId) {
        super(BookmarkErrorCode.ALREADY_EXISTS, Map.of(
                "groupId", groupId.value(),
                "contentId", contentId.value()
        ));
    }
}
