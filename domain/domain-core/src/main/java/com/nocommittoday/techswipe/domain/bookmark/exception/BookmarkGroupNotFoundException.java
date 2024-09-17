package com.nocommittoday.techswipe.domain.bookmark.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.UserId;

import java.util.Map;

public class BookmarkGroupNotFoundException extends AbstractDomainException {

    public BookmarkGroupNotFoundException(UserId userId, String name) {
        super(BookmarkErrorCode.GROUP_NOT_FOUND, Map.of(
                "userId", userId.value(),
                "name", name
        ));
    }
}
