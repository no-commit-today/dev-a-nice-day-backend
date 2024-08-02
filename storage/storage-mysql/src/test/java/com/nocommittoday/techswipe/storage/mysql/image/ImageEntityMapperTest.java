package com.nocommittoday.techswipe.storage.mysql.image;


import com.nocommittoday.techswipe.image.domain.ImageId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ImageEntityMapperTest {

    @InjectMocks
    private ImageEntityMapper imageEntityMapper;

    @Mock
    private ImageJpaRepository imageJpaRepository;

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        ImageEntity imageEntity = mock(ImageEntity.class);
        given(imageJpaRepository.getReferenceById(1L)).willReturn(imageEntity);
        ImageId id = new ImageId(1);

        // when
        ImageEntity result = imageEntityMapper.from(id);

        // then
        assertThat(result).isEqualTo(imageEntity);
    }

    @Test
    void 도메인_엔티티가_null일_경우_null을_리턴한다() {
        // given
        ImageId id = null;

        // when
        ImageEntity result = imageEntityMapper.from(id);

        // then
        assertThat(result).isNull();
    }

}