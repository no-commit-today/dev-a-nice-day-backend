package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookmarkGroupWithContainsListReader {

    private final BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    public BookmarkGroupWithContainsListReader(BookmarkEntityJpaRepository bookmarkEntityJpaRepository) {
        this.bookmarkEntityJpaRepository = bookmarkEntityJpaRepository;
    }

    public List<BookmarkGroupWithContains> getList(List<BookmarkGroup> groups, TechContentId contentId) {
        Set<BookmarkGroupId> groupIdContainingContent = bookmarkEntityJpaRepository.findAllByContentId(contentId.value())
                .stream().map(bookmark -> new BookmarkGroupId(bookmark.getGroup().getId()))
                .collect(Collectors.toSet());
        return groups.stream()
                .map(group -> new BookmarkGroupWithContains(group, groupIdContainingContent.contains(group.getId())))
                .toList();
    }
}
