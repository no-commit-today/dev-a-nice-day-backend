package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechContentQuery;
import com.nocommittoday.techswipe.storage.mysql.test.TechContentEntityBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentEntityTest {


    @Test
    void 쿼리도메인객체로_변환할_수_있다() {
        // given
        TechContentEntity entity = TechContentEntityBuilder.create();

        // when
        TechContentQuery query = entity.toQuery();

        // then
        assertThat(query.getId().value()).isEqualTo(entity.getId());
        assertThat(query.getProvider().getId().value()).isEqualTo(entity.getProvider().getId());
        assertThat(query.getTitle()).isEqualTo(entity.getTitle());
        assertThat(query.getUrl()).isEqualTo(entity.getUrl());
        assertThat(query.getPublishedDate()).isEqualTo(entity.getPublishedDate());
        assertThat(query.getCategories()).isEqualTo(entity.getCategories());
        assertThat(query.getImageUrl()).isNull();
    }
}