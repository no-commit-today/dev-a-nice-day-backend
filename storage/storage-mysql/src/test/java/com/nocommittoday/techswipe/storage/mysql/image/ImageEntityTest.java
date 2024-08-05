package com.nocommittoday.techswipe.storage.mysql.image;

import com.nocommittoday.techswipe.storage.mysql.test.ImageEntityBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageEntityTest {

    @Test
    void url을_추출할_수_있다() {
        // given
        ImageEntity entity = ImageEntityBuilder.create();

        // when
        String url = ImageEntity.url(entity);

        // then
        assertThat(url).isEqualTo(entity.getUrl());
    }

    @Test
    void 엔티티에_null이_들어왔을_때_url추출_결과는_null이다() {
        // given
        ImageEntity entity = null;

        // when
        String url = ImageEntity.url(entity);

        // then
        assertThat(url).isNull();
    }

    @Test
    void 엔티티가_삭제되었을때_url추출_결과는_null이다() {
        // given
        ImageEntity entity = ImageEntityBuilder.create();
        entity.delete();

        // when
        String url = ImageEntity.url(entity);

        // then
        assertThat(url).isNull();
    }

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        ImageEntity entity = ImageEntityBuilder.create();
        entity.delete();

        // when
        String url = ImageEntity.url(entity);

        // then
        assertThat(url).isNull();
    }
}