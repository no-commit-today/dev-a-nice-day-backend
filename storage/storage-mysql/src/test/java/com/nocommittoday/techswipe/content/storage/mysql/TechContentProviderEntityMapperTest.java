package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProviderCreate;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class TechContentProviderEntityMapperTest {

    @InjectMocks
    private TechContentProviderEntityMapper techContentProviderEntityMapper;

    @Mock
    private TechContentProviderJpaRepository techContentProviderJpaRepository;

    @Mock
    private ImageEntityMapper imageEntityMapper;

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        final TechContentProviderEntity entity = mock(TechContentProviderEntity.class);
        given(techContentProviderJpaRepository.getReferenceById(1L)).willReturn(entity);

        // when
        final TechContentProviderEntity result = techContentProviderEntityMapper.from(new TechContentProviderId(1));

        // then
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void TechContentProviderCreate로부터_생성할_수_있다() {
        // given
        final TechContentProviderCreate command = new TechContentProviderCreate(
                new TechContentProviderId(1),
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                new ImageId(1)
        );

        final ImageEntity imageEntity = mock(ImageEntity.class);
        given(imageEntityMapper.from(new ImageId(1))).willReturn(imageEntity);

        // when
        final TechContentProviderEntity result = techContentProviderEntityMapper.from(command);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getType()).isEqualTo(TechContentProviderType.DOMESTIC_COMPANY_BLOG);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getIcon()).isEqualTo(imageEntity);
    }
}