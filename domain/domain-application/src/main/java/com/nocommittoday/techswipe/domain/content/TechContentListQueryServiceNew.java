package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkChecker;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkCheckerCreator;
import com.nocommittoday.techswipe.domain.user.ApiUserOrGuest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechContentListQueryServiceNew {

    private final TechContentListQueryReaderNew techContentListQueryReader;
    private final BookmarkCheckerCreator bookmarkCheckerCreator;

    public TechContentListQueryServiceNew(
            TechContentListQueryReaderNew techContentListQueryReader,
            BookmarkCheckerCreator bookmarkCheckerCreator
    ) {
        this.techContentListQueryReader = techContentListQueryReader;
        this.bookmarkCheckerCreator = bookmarkCheckerCreator;
    }

    public TechContentListQueryResult getList(ApiUserOrGuest apiUserOrGuest, TechContentListQueryParamNew param) {
        List<TechContentQuery> contents = techContentListQueryReader.getList(param);
        if (apiUserOrGuest.isGuest()) {
            return new TechContentListQueryResult(
                    contents.stream()
                            .map(content -> new TechContentQueryResult(content, false))
                            .toList()
            );
        }

        List<TechContentId> contentIds = contents.stream()
                .map(TechContentQuery::getId)
                .toList();
        BookmarkChecker bookmarkChecker = bookmarkCheckerCreator.create(apiUserOrGuest.getUserId(), contentIds);

        return new TechContentListQueryResult(
                contents.stream()
                        .map(content -> new TechContentQueryResult(content, bookmarkChecker.check(content.getId())))
                        .toList()
        );
    }
}
