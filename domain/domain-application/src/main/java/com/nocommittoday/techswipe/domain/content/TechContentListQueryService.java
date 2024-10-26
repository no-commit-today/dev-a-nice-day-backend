package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkChecker;
import com.nocommittoday.techswipe.domain.content.bookmark.BookmarkCheckerCreator;
import com.nocommittoday.techswipe.domain.core.PageParam;
import com.nocommittoday.techswipe.domain.user.ApiUserOrGuest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechContentListQueryService {

    private final TechContentListQueryReader techContentListQueryReader;
    private final TechContentCountReaderLocalCache techContentCountReader;
    private final BookmarkCheckerCreator bookmarkCheckerCreator;

    public TechContentListQueryService(
            TechContentListQueryReader techContentListQueryReader,
            TechContentCountReaderLocalCache techContentCountReader,
            BookmarkCheckerCreator bookmarkCheckerCreator
    ) {
        this.techContentListQueryReader = techContentListQueryReader;
        this.techContentCountReader = techContentCountReader;
        this.bookmarkCheckerCreator = bookmarkCheckerCreator;
    }

    public TechContentListQueryResult getList(
            ApiUserOrGuest apiUserOrGuest, PageParam pageParam, TechContentListQueryParam queryParam
    ) {
        List<TechContentQuery> contents = techContentListQueryReader.getListV1(pageParam, queryParam.categories());
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

    public long count(TechContentListQueryParam queryParam) {
        return techContentCountReader.get(queryParam.categories());
    }
}
