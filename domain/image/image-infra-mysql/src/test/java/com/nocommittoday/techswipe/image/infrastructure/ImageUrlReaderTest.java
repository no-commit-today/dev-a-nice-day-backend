package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.domain.image.Image;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.image.ImageReader;
import com.nocommittoday.techswipe.domain.image.ImageUrlReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageUrlReaderTest {

    @InjectMocks
    private ImageUrlReader imageUrlReader;

    @Mock
    private ImageReader imageReader;

    @Test
    void url을_조회할_수_있다() {
        // given
        ImageId id = new ImageId(1L);
        Image image = new Image(
                id,
                "url",
                "original-url",
                "stored-name"
        );

        given(imageReader.get(id)).willReturn(image);

        // when
        String url = imageUrlReader.get(id);

        // then
        assertThat(url).isEqualTo("url");
    }

    @Test
    void null이_들어오면_null을_반환한다() {
        // when
        String url = imageUrlReader.getOrNull(null);

        // then
        assertThat(url).isNull();
    }

    @Test
    void null이_아닌_값이_들어오면_url을_반환한다() {
        // given
        ImageId id = new ImageId(1L);
        Image image = new Image(
                id,
                "url",
                "original-url",
                "stored-name"
        );

        given(imageReader.get(id)).willReturn(image);

        // when
        String url = imageUrlReader.getOrNull(id);

        // then
        assertThat(url).isEqualTo("url");
    }
}