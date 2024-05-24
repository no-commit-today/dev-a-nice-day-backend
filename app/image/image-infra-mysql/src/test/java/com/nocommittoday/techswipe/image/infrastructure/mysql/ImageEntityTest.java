package com.nocommittoday.techswipe.image.infrastructure.mysql;

import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageEntityTest {

    @Test
    void toDomain() {
        // given
        final ImageEntity imageEntity = new ImageEntity(
                1L,
                "http://example.com/image.jpg",
                "http://original.com/image.jpg",
                "image.jpg"
        );

        // when
        Image image = imageEntity.toDomain();

        // then
        assertThat(image.getId()).isEqualTo(new Image.ImageId(1));
        assertThat(image.getUrl()).isEqualTo("http://example.com/image.jpg");
        assertThat(image.getOriginalUrl()).isEqualTo("http://original.com/image.jpg");
        assertThat(image.getStoredName()).isEqualTo("image.jpg");
    }
}