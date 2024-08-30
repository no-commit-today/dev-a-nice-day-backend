package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.domain.content.TechContentProviderQuery;
import com.nocommittoday.techswipe.storage.mysql.test.TechContentProviderEntityBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderEntityTest {

    @Test
    void 쿼리도메인객체로_변환할_수_있다() {
        // given
        TechContentProviderEntity entity = TechContentProviderEntityBuilder.create();

        // when
        TechContentProviderQuery query = entity.toQuery();

        // then
        assertThat(query.getId().value()).isEqualTo(entity.getId());
        assertThat(query.getType()).isEqualTo(entity.getType());
        assertThat(query.getTitle()).isEqualTo(entity.getTitle());
        assertThat(query.getUrl()).isEqualTo(entity.getUrl());
        assertThat(query.getIconUrl()).isNull();
    }
}