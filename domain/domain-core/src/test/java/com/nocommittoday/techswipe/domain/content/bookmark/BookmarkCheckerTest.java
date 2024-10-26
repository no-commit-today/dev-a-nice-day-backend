package com.nocommittoday.techswipe.domain.content.bookmark;

import com.nocommittoday.techswipe.domain.content.TechContentId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookmarkCheckerTest {

    @Test
    void 북마크된_컨텐츠를_확인할_수_있다() {
        // given
        BookmarkChecker checker = BookmarkChecker.create(List.of(
                        new BookmarkBuilder().contentId(new TechContentId(1)).build(),
                        new BookmarkBuilder().contentId(new TechContentId(13)).build(),
                        new BookmarkBuilder().contentId(new TechContentId(249)).build()
                )
        );

        // when
        // then
        assertThat(checker.check(new TechContentId(1))).isTrue();
        assertThat(checker.check(new TechContentId(13))).isTrue();
        assertThat(checker.check(new TechContentId(249))).isTrue();
        assertThat(checker.check(new TechContentId(2))).isFalse();
        assertThat(checker.check(new TechContentId(14))).isFalse();
        assertThat(checker.check(new TechContentId(250))).isFalse();
    }

}