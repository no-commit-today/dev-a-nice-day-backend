package com.nocommittoday.techswipe.domain.bookmark;

import com.nocommittoday.techswipe.domain.bookmark.exception.BookmarkIllegalGroupNameException;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntity;
import com.nocommittoday.techswipe.storage.mysql.bookmark.BookmarkGroupEntityJpaRepository;
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
class BookmarkGroupAppenderTest {

    @InjectMocks
    private BookmarkGroupAppender bookmarkGroupAppender;

    @Mock
    private BookmarkGroupEntityJpaRepository bookmarkGroupEntityJpaRepository;

    @Captor
    private ArgumentCaptor<BookmarkGroupEntity> bookmarkGroupEntityArgumentCaptor;

    @Test
    void 북마크_그룹을_추가한다() {
        // given
        given(bookmarkGroupEntityJpaRepository.findByUserIdAndName(1L, "그룹"))
                .willReturn(Optional.empty());
        BookmarkGroupEntity savedEntity = mock(BookmarkGroupEntity.class);
        given(savedEntity.getId()).willReturn(10L);
        given(bookmarkGroupEntityJpaRepository.save(bookmarkGroupEntityArgumentCaptor.capture()))
                .willReturn(savedEntity);

        // when
        BookmarkGroupId result = bookmarkGroupAppender.append(new UserId(1L), "그룹");

        // then
        BookmarkGroupEntity bookmarkGroupEntity = bookmarkGroupEntityArgumentCaptor.getValue();
        assertThat(bookmarkGroupEntity.getUserId()).isEqualTo(1L);
        assertThat(bookmarkGroupEntity.getName()).isEqualTo("그룹");
        assertThat(result.value()).isEqualTo(10L);
    }

    @Test
    void 북마크_그룹이_존재할_경우_예외를_발생시킨다() {
        // given
        given(bookmarkGroupEntityJpaRepository.findByUserIdAndName(1L, "그룹"))
                .willReturn(Optional.of(mock(BookmarkGroupEntity.class)));

        // when
        // then
        assertThatThrownBy(() -> bookmarkGroupAppender.append(new UserId(1L), "그룹"))
                .isInstanceOf(BookmarkIllegalGroupNameException.class);
    }
}