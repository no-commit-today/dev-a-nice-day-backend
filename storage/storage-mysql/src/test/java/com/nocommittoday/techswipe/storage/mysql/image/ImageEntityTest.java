package com.nocommittoday.techswipe.storage.mysql.image;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImageEntityTest {

    @Test
    void 도메인_엔티티로_전환할_수_있다() {
        // given
        ImageEntity imageEntity = new ImageEntity(
                1L,
                "http://example.com/image.jpg",
                "http://original.com/image.jpg",
                "image.jpg"
        );

        // when
        Image image = imageEntity.toDomain();

        // then
        assertThat(image.getId()).isEqualTo(new ImageId(1));
        assertThat(image.getUrl()).isEqualTo("http://example.com/image.jpg");
        assertThat(image.getOriginalUrl()).isEqualTo("http://original.com/image.jpg");
        assertThat(image.getStoredName()).isEqualTo("image.jpg");
    }
}