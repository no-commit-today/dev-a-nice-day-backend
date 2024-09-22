package com.nocommittoday.techswipe.domain.bookmark.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.user.UserId;

import java.util.Map;

public class BookmarkIllegalGroupNameException extends AbstractDomainException {

    public BookmarkIllegalGroupNameException(UserId userId, String name) {
        super(BookmarkErrorCode.ILLEGAL_GROUP_NAME, Map.of(
                "userId", userId.value(),
                "name", name
        ));
    }
}
