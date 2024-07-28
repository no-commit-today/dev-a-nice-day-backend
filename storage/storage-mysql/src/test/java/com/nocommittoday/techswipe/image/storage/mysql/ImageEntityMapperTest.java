package com.nocommittoday.techswipe.image.storage.mysql;


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
        final ImageEntity imageEntity = mock(ImageEntity.class);
        given(imageJpaRepository.getReferenceById(1L)).willReturn(imageEntity);
        final ImageId id = new ImageId(1);

        // when
        final ImageEntity result = imageEntityMapper.from(id);

        // then
        assertThat(result).isEqualTo(imageEntity);
    }

    @Test
    void 도메인_엔티티가_null일_경우_null을_리턴한다() {
        // given
        final ImageId id = null;

        // when
        final ImageEntity result = imageEntityMapper.from(id);

        // then
        assertThat(result).isNull();
    }

}