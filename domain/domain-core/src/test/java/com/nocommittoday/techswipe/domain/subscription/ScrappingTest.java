package com.nocommittoday.techswipe.domain.subscription;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScrappingTest {

    @Test
    void None타입_Scrapping_을_생성할_수_있다() {
        // given
        Scrapping scrapping = Scrapping.createNoneScrapping();

        // when
        // then
        assertThat(scrapping.getType()).isEqualTo(ScrappingType.NONE);
    }

    @Test
    void Index타입_Scrapping_을_생성할_수_있다() {
        // given
        Scrapping scrapping = Scrapping.createIndexScrapping(List.of(1, 2, 3));

        // when
        // then
        assertThat(scrapping.getType()).isEqualTo(ScrappingType.INDEX);
        assertThat(scrapping).isInstanceOf(IndexScrapping.class);
    }

    @Test
    void Selector타입_Scrapping_을_생성할_수_있다() {
        // given
        Scrapping scrapping = Scrapping.createSelectorScrapping("selector");

        // when
        // then
        assertThat(scrapping.getType()).isEqualTo(ScrappingType.SELECTOR);
        assertThat(scrapping).isInstanceOf(SelectorScrapping.class);
    }

    @Test
    void Title타입_Scrapping_을_생성할_수_있다() {
        // given
        Scrapping scrapping = Scrapping.createTitleScrapping();

        // when
        // then
        assertThat(scrapping.getType()).isEqualTo(ScrappingType.TITLE);
    }

    @Test
    void OpenGraphImage타입_Scrapping_을_생성할_수_있다() {
        // given
        Scrapping scrapping = Scrapping.createOpenGraphImageScrapping();

        // when
        // then
        assertThat(scrapping.getType()).isEqualTo(ScrappingType.OPEN_GRAPH_IMAGE);
    }

}