package com.devniceday.batch.domain;

import com.devniceday.batch.domain.test.ImageScrappingFixture;
import com.devniceday.batch.domain.test.ScrappingFixture;
import com.devniceday.batch.jsoup.HtmlDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HtmlScrapperTest {

    private HtmlScrapper htmlScrapper = new HtmlScrapper();

    @Mock
    private HtmlDocument htmlDocument;

    @Test
    void 인덱스로_텍스트를_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.index();
        given(htmlDocument.textByIndex(scrapping.indexes())).willReturn("스크랩된 텍스트");

        // when
        String result = htmlScrapper.scrapText(htmlDocument, scrapping);

        // then
        assertThat(result).isEqualTo("스크랩된 텍스트");
    }

    @Test
    void 셀렉터로_텍스트를_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.selector();
        given(htmlDocument.textBySelector(scrapping.selector())).willReturn("스크랩된 텍스트");

        // when
        String result = htmlScrapper.scrapText(htmlDocument, scrapping);

        // then
        assertThat(result).isEqualTo("스크랩된 텍스트");
    }

    @Test
    void 타이틀을_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.title();
        given(htmlDocument.title()).willReturn("페이지 제목");

        // when
        String result = htmlScrapper.scrapText(htmlDocument, scrapping);

        // then
        assertThat(result).isEqualTo("페이지 제목");
    }

    @Test
    void NONE_타입은_null을_반환한다() {
        // given
        Scrapping scrapping = ScrappingFixture.none();

        // when
        String result = htmlScrapper.scrapText(htmlDocument, scrapping);

        // then
        assertThat(result).isNull();
    }

    @Test
    void 오픈그래프_이미지를_스크랩할_수_있다() {
        // given
        ImageScrapping scrapping = ImageScrappingFixture.create();
        given(htmlDocument.openGraphImage()).willReturn("이미지 URL");

        // when
        String result = htmlScrapper.scrapImage(htmlDocument, scrapping);

        // then
        assertThat(result).isEqualTo("이미지 URL");
    }

    @Test
    void 인덱스로_HTML을_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.index();
        given(htmlDocument.htmlByIndexes(scrapping.indexes())).willReturn("<div>HTML 내용</div>");

        // when
        String result = htmlScrapper.scrapHtml(htmlDocument, scrapping);

        // then
        assertThat(result).isEqualTo("<div>HTML 내용</div>");
    }

    @Test
    void 셀렉터로_HTML을_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.selector();
        given(htmlDocument.htmlBySelector(scrapping.selector())).willReturn("<div>HTML 내용</div>");

        // when
        String result = htmlScrapper.scrapHtml(htmlDocument, scrapping);

        // then
        assertThat(result).isEqualTo("<div>HTML 내용</div>");
    }

    @Test
    void NONE_타입으로_HTML을_스크랩하면_null을_반환한다() {
        // given
        Scrapping scrapping = ScrappingFixture.none();

        // when
        String result = htmlScrapper.scrapHtml(htmlDocument, scrapping);

        // then
        assertThat(result).isNull();
    }

    @Test
    void 지원하지_않는_타입으로_HTML을_스크랩하면_예외가_발생한다() {
        // given
        Scrapping scrapping = ScrappingFixture.title();

        // when
        // then
        assertThatThrownBy(() -> htmlScrapper.scrapHtml(htmlDocument, scrapping))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 셀렉터로_URL_리스트를_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.selector();
        given(htmlDocument.urlListBySelector(scrapping.selector())).willReturn(
                List.of("http://example1.com", "http://example2.com")
        );

        // when
        List<String> result = htmlScrapper.scrapUrls(htmlDocument, scrapping);

        // then
        assertThat(result)
                .hasSize(2)
                .containsExactly("http://example1.com", "http://example2.com");
    }

    @Test
    void 인덱스로_URL_리스트를_스크랩할_수_있다() {
        // given
        Scrapping scrapping = ScrappingFixture.index();
        given(htmlDocument.urlListByIndexes(scrapping.indexes())).willReturn(
                List.of("http://example1.com", "http://example2.com")
        );

        // when
        List<String> result = htmlScrapper.scrapUrls(htmlDocument, scrapping);

        // then
        assertThat(result)
                .hasSize(2)
                .containsExactly("http://example1.com", "http://example2.com");
    }
}