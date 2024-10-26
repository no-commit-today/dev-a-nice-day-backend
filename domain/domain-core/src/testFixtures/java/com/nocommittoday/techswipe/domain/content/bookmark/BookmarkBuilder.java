package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.test.LocalAutoIncrementIdUtils;

import javax.annotation.Nullable;

public class BookmarkBuilder {

    @Nullable
    private BookmarkId id;

    @Nullable
    private BookmarkGroupId groupId;

    @Nullable
    private TechContentId contentId;

    public BookmarkBuilder id(BookmarkId id) {
        this.id = id;
        return this;
    }

    public BookmarkBuilder groupId(BookmarkGroupId groupId) {
        this.groupId = groupId;
        return this;
    }

    public BookmarkBuilder contentId(TechContentId contentId) {
        this.contentId = contentId;
        return this;
    }

    public Bookmark build() {
        fillRequiredFields();
        return new Bookmark(id, groupId, contentId);
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = new BookmarkId(LocalAutoIncrementIdUtils.nextId());
        }
        if (groupId == null) {
            groupId = new BookmarkGroupId(LocalAutoIncrementIdUtils.nextId());
        }
        if (contentId == null) {
            contentId = new TechContentId(LocalAutoIncrementIdUtils.nextId());
        }
    }
}
