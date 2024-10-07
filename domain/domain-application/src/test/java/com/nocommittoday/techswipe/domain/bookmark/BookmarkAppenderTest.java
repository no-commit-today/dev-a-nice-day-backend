package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkAlreadyExistsException;
import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkGroupNotFoundException;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BookmarkAppenderTest {

    @InjectMocks
    private BookmarkAppender bookmarkAppender;

    @Mock
    private TechContentJpaRepository techContentJpaRepository;

    @Mock
    private BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    @Mock
    private BookmarkEntityJpaRepository bookmarkEntityJpaRepository;

    @Captor
    ArgumentCaptor<BookmarkEntity> bookmarkEntityArgumentCaptor;

    @Test
    void 북마크를_추가한다() {
        // given
        BookmarkGroupEntity bookmarkGroup = mock(BookmarkGroupEntity.class);
        TechContentEntity content = mock(TechContentEntity.class);
        given(content.isUsed()).willReturn(true);

        given(bookmarkGroupEntityJpaRepository.findById(1L)).willReturn(Optional.of(bookmarkGroup));
        given(techContentJpaRepository.findById(2L)).willReturn(Optional.of(content));
        given(bookmarkEntityJpaRepository.findByGroupAndContent(bookmarkGroup, content)).willReturn(Optional.empty());

        BookmarkEntity savedBookmark = mock(BookmarkEntity.class);
        given(savedBookmark.getId()).willReturn(3L);
        given(bookmarkEntityJpaRepository.save(bookmarkEntityArgumentCaptor.capture())).willReturn(savedBookmark);

        // when
        BookmarkId bookmarkId = bookmarkAppender.append(new BookmarkGroupId(1L), new TechContentId(2L));

        // then
        BookmarkEntity bookmarkEntity = bookmarkEntityArgumentCaptor.getValue();
        assertThat(bookmarkEntity.getGroup()).isEqualTo(bookmarkGroup);
        assertThat(bookmarkEntity.getContent()).isEqualTo(content);
        assertThat(bookmarkId.value()).isEqualTo(3L);
    }

    @Test
    void 북마크가_이미_존재하는_경우_에러가_발생한다() {
        // given
        BookmarkGroupEntity bookmarkGroup = mock(BookmarkGroupEntity.class);
        TechContentEntity content = mock(TechContentEntity.class);
        given(content.isUsed()).willReturn(true);

        given(bookmarkGroupEntityJpaRepository.findById(1L)).willReturn(Optional.of(bookmarkGroup));
        given(techContentJpaRepository.findById(2L)).willReturn(Optional.of(content));
        given(bookmarkEntityJpaRepository.findByGroupAndContent(bookmarkGroup, content)).willReturn(Optional.of(mock(BookmarkEntity.class)));

        // when, then
        assertThatThrownBy(() -> bookmarkAppender.append(new BookmarkGroupId(1L), new TechContentId(2L)))
                .isInstanceOf(BookmarkAlreadyExistsException.class);
    }

    @Test
    void 존재하지_않는_북마크그룹에_북마크를_추가하려고_하는_경우_에러가_발생한다() {
        // given
        given(bookmarkGroupEntityJpaRepository.findById(1L)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> bookmarkAppender.append(new BookmarkGroupId(1L), new TechContentId(2L))
        ).isInstanceOf(BookmarkGroupNotFoundException.class);
    }

    @Test
    void 삭제된_컨텐츠를_북마크하려고_하는_경우_에러가_발생한다() {
        // given
        BookmarkGroupEntity bookmarkGroup = mock(BookmarkGroupEntity.class);
        given(bookmarkGroupEntityJpaRepository.findById(1L)).willReturn(Optional.of(bookmarkGroup));

        TechContentEntity content = mock(TechContentEntity.class);
        given(content.isUsed()).willReturn(false);

        given(techContentJpaRepository.findById(2L)).willReturn(Optional.of(content));

        // when, then
        assertThatThrownBy(() -> bookmarkAppender.append(new BookmarkGroupId(1L), new TechContentId(2L)))
                .isInstanceOf(TechContentNotFoundException.class);
    }
}