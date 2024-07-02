package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.domain.ImageNotFoundException;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageIdValidatorTest {

    @InjectMocks
    private ImageIdValidator imageIdValidator;

    @Mock
    private ImageJpaRepository imageJpaRepository;

    @Test
    void 이미지_ID가_존재하지_않으면_예외를_던진다() {
        // given
        final Image.Id imageId = new Image.Id(1L);
        given(imageJpaRepository.existsByIdAndDeletedIsFalse(1L))
                .willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> imageIdValidator.validate(imageId))
                .isInstanceOf(ImageNotFoundException.class);
    }
}