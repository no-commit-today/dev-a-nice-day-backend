package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageIdEmbeddableTest {

    @Test
    void toDomain() {
        // given
        final ImageIdEmbeddable imageIdEmbeddable = new ImageIdEmbeddable(1L);

        // when
        final Image.ImageId result = imageIdEmbeddable.toDomain();

        // then
        assertThat(result).isEqualTo(new Image.ImageId(1));
    }
}