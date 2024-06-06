package com.nocommittoday.techswipe.image.storage.mysql;

import com.nocommittoday.techswipe.image.domain.Image;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageEntityTest {

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        final Image.ImageId id = new Image.ImageId(1);

        // when
        final ImageEntity result = ImageEntity.from(id);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void 도메인_엔티티_ID로_전환할_수_있다() {
        // given
        final ImageEntity imageEntity = new ImageEntity(
                1L,
                "http://example.com/image.jpg",
                "http://original.com/image.jpg",
                "image.jpg"
        );

        // when
        Image.ImageId imageId = imageEntity.toDomainId();

        // then
        assertThat(imageId).isEqualTo(new Image.ImageId(1));
    }

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
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