package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.exception.ImageNotFoundException;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageReaderTest {

    @InjectMocks
    private ImageReader imageReader;

    @Mock
    private ImageJpaRepository imageRepository;

    @Test
    void 이미지를_조회할_수_있다() {
        // given
        final ImageEntity imageEntity = new ImageEntity(
                1L,
                "url",
                "original-url",
                "stored-name"
        );
        given(imageRepository.findById(1L))
                .willReturn(Optional.of(imageEntity));

        // when
        final Image image = imageReader.get(new Image.ImageId(1L));

        // then
        assertThat(image).isNotNull();
    }

    @Test
    void 삭제된_이미지는_조회할_수_없다() {
        // given
        final ImageEntity imageEntity = new ImageEntity(
                1L,
                "url",
                "original-url",
                "stored-name"
        );
        imageEntity.delete();
        given(imageRepository.findById(1L))
                .willReturn(Optional.of(imageEntity));

        // when
        // then
        assertThatThrownBy(() -> imageReader.get(new Image.ImageId(1L)))
                .isInstanceOf(ImageNotFoundException.class);
    }

    @Test
    void 이미지_목록을_조회할_수_있다() {
        // given
        final ImageEntity imageEntity1 = new ImageEntity(
                1L,
                "url",
                "original-url",
                "stored-name"
        );
        final ImageEntity imageEntity2 = new ImageEntity(
                2L,
                "url",
                "original-url",
                "stored-name"
        );
        given(imageRepository.findAllById(List.of(1L, 2L)))
                .willReturn(List.of(imageEntity1, imageEntity2));

        // when
        final List<Image> images = imageReader.getAll(List.of(
                new Image.ImageId(1L), new Image.ImageId(2L)
        ));

        // then
        assertThat(images).hasSize(2);
    }

    @Test
    void 삭제된_이미지는_목록에서_제외된다() {
        // given
        final ImageEntity imageEntity1 = new ImageEntity(
                1L,
                "url",
                "original-url",
                "stored-name"
        );
        final ImageEntity imageEntity2 = new ImageEntity(
                2L,
                "url",
                "original-url",
                "stored-name"
        );
        imageEntity2.delete();
        given(imageRepository.findAllById(List.of(1L, 2L)))
                .willReturn(List.of(imageEntity1, imageEntity2));

        // when
        final List<Image> images = imageReader.getAll(List.of(
                new Image.ImageId(1L), new Image.ImageId(2L)
        ));

        // then
        assertThat(images).hasSize(1);
    }
}