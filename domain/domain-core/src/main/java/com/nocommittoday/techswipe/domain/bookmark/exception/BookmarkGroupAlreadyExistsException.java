package com.nocommittoday.techswipe.domain.bookmark.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.UserId;

import java.util.Map;

public class BookmarkGroupAlreadyExistsException extends AbstractDomainException {

    public BookmarkGroupAlreadyExistsException(UserId userId, String name) {
        super(BookmarkErrorCode.GROUP_ALREADY_EXISTS, Map.of(
                "userId", userId.value(),
                "name", name
        ));
    }
}
