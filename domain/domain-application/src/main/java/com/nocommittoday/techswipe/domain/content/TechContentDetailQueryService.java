package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkChecker;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkCheckerCreator;
import com.nocommittoday.techswipe.domain.user.ApiUserOrGuest;
import org.springframework.stereotype.Service;

@Service
public class TechContentDetailQueryService {

    private final TechContentQueryReader techContentQueryReader;
    private final BookmarkCheckerCreator bookmarkCheckerCreator;

    public TechContentDetailQueryService(
            TechContentQueryReader techContentQueryReader,
            BookmarkCheckerCreator bookmarkCheckerCreator
    ) {
        this.techContentQueryReader = techContentQueryReader;
        this.bookmarkCheckerCreator = bookmarkCheckerCreator;
    }

    public TechContentDetailQueryResult get(ApiUserOrGuest apiUserOrGuest, TechContentId id) {
        TechContentQuery techContent = techContentQueryReader.get(id);

        if (apiUserOrGuest.isGuest()) {
            return new TechContentDetailQueryResult(
                    techContent,
                    false
            );
        }

        BookmarkChecker checker = bookmarkCheckerCreator.create(apiUserOrGuest.getUserId(), techContent.getId());
        return new TechContentDetailQueryResult(
                techContent,
                checker.check(techContent.getId())
        );
    }
}
