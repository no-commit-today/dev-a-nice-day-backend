package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.domain.test.ScrappingBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ListScrappingTest {

    @Test
    void pageUrlFormat이_올바른_형식이_아닐경우_예외가_발생한다() {
        // given
        assertThatThrownBy(() ->
                new ListScrapping(
                        "list-url",
                        ScrappingBuilder.selector(),
                        "page-url-format-invalid",
                        null
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void contentUrlFormat이_올바른_형식이_아닐경우_예외가_발생한다() {
        // given
        assertThatThrownBy(() ->
                new ListScrapping(
                        "list-url",
                        ScrappingBuilder.selector(),
                        null,
                        "content-url-format-invalid"
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void pageUrlFormat이_있을경우_페이지네이션이_지원된다() {
        // given
        ListScrapping nonPaginatedListScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                null,
                null
        );
        ListScrapping paginatedListScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                "page-url?=" + ListScrapping.PAGE_URL_PAGE_PLACEHOLDER,
                null
        );

        // when
        // then
        assertThat(nonPaginatedListScrapping.isPaginated()).isFalse();
        assertThat(paginatedListScrapping.isPaginated()).isTrue();
    }

    @Test
    void 페이지네이션을_지원하지_않으면_pageUrl_을_만들_수_없다() {
        // given
        ListScrapping nonPaginatedListScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                null,
                null
        );

        // when
        // then
        assertThatThrownBy(() -> nonPaginatedListScrapping.getPageUrl(1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 페이지네이션을_지원하면_pageUrl_을_만들_수_있다() {
        // given
        ListScrapping paginatedListScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                "page-url?page=" + ListScrapping.PAGE_URL_PAGE_PLACEHOLDER,
                null
        );

        // when
        String pageUrl = paginatedListScrapping.getPageUrl(2);

        // then
        assertThat(pageUrl).isEqualTo("page-url?page=2");
    }

    @Test
    void contentUrlFormat이_없으면_모든_url이_contentUrl이다() {
        // given
        ListScrapping listScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                null,
                null
        );

        // when
        // then
        assertThat(listScrapping.isContentUrl("content-url")).isTrue();
        assertThat(listScrapping.isContentUrl("asdf")).isTrue();
    }

    @Test
    void null_이거나_빈_문자열은_contentUrl이_아니다() {
        // given
        ListScrapping listScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                null,
                null
        );

        // when
        // then
        assertThat(listScrapping.isContentUrl(null)).isFalse();
        assertThat(listScrapping.isContentUrl("")).isFalse();
    }

    @Test
    void contentUrlFormat이_있으면_해당_형식에_맞는_url만_contentUrl이다() {
        // given
        ListScrapping listScrapping = new ListScrapping(
                "list-url",
                ScrappingBuilder.selector(),
                null,
                "content-url/" + ListScrapping.CONTENT_URL_ID_PLACEHOLDER
        );

        // when
        // then
        assertThat(listScrapping.isContentUrl("content-url")).isFalse();
        assertThat(listScrapping.isContentUrl("content-url/")).isFalse();
        assertThat(listScrapping.isContentUrl("content-url/1/")).isFalse();
        assertThat(listScrapping.isContentUrl("content-url/1/2")).isFalse();

        assertThat(listScrapping.isContentUrl("content-url/1")).isTrue();
        assertThat(listScrapping.isContentUrl("content-url/content-id")).isTrue();
        assertThat(listScrapping.isContentUrl("content-url/컨텐츠_아이디_한글")).isTrue();
    }
}