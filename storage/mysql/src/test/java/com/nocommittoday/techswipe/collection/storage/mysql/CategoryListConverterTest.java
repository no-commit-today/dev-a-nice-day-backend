package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryListConverterTest {

    @Test
    void attribute가_null일_경우_칼럼값_null을_반환한다() {
        // given
        CategoryListConverter converter = new CategoryListConverter();

        // when
        String result = converter.convertToDatabaseColumn(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void attribute가_비어있을_경우_칼럼값_null을_반환한다() {
        // given
        CategoryListConverter converter = new CategoryListConverter();

        // when
        String result = converter.convertToDatabaseColumn(List.of());

        // then
        assertThat(result).isNull();
    }

    @Test
    void attribute의_리스트값들을_사전순_정렬하고_콤마로_구분하여_문자열로_변환한다() {
        // given
        CategoryListConverter converter = new CategoryListConverter();

        // when
        String result = converter.convertToDatabaseColumn(List.of(TechCategory.INFRA, TechCategory.BACKEND));

        // then
        assertThat(result).isEqualTo("BACKEND,INFRA");
    }

    @Test
    void dbData가_null일_경우_빈_리스트를_반환한다() {
        // given
        CategoryListConverter converter = new CategoryListConverter();

        // when
        List<TechCategory> result = converter.convertToEntityAttribute(null);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void dbData가_비어있을_경우_빈_리스트를_반환한다() {
        // given
        CategoryListConverter converter = new CategoryListConverter();

        // when
        List<TechCategory> result = converter.convertToEntityAttribute("");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void dbData를_콤마로_구분하여_리스트로_변환한다() {
        // given
        CategoryListConverter converter = new CategoryListConverter();

        // when
        List<TechCategory> result = converter.convertToEntityAttribute("BACKEND,INFRA");

        // then
        assertThat(result).containsExactly(TechCategory.BACKEND, TechCategory.INFRA);
    }

}