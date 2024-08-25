package com.nocommittoday.techswipe.storage.mysql.batch;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.TechContentCreate;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BatchTechContentEntityMapperTest {

    @InjectMocks
    private BatchTechContentEntityMapper techContentEntityMapper;

    @Mock
    private TechContentJpaRepository techContentJpaRepository;

    @Mock
    private BatchTechContentProviderEntityMapper techContentProviderEntityMapper;

    @Mock
    private BatchImageEntityMapper imageEntityMapper;

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        TechContentEntity entity = mock(TechContentEntity.class);
        given(techContentJpaRepository.getReferenceById(1L)).willReturn(entity);

        // when
        TechContentEntity result = techContentEntityMapper.from(new TechContentId(1));

        // then
        assertThat(result).isEqualTo(entity);
    }

    @Test
    void TechContentCreate로부터_생성할_수_있다() {
        // given
        TechContentCreate command = new TechContentCreate(
                new TechContentId(1),
                new TechContentProviderId(2),
                "url",
                "title",
                LocalDate.of(2021, 1, 1),
                new ImageId(3),
                "summary",
                List.of(TechCategory.SERVER, TechCategory.SW_ENGINEERING)
        );

        ImageEntity imageEntity = mock(ImageEntity.class);
        given(imageEntityMapper.from(new ImageId(3))).willReturn(imageEntity);

        TechContentProviderEntity providerEntity = mock(TechContentProviderEntity.class);
        given(techContentProviderEntityMapper.from(new TechContentProviderId(2))).willReturn(providerEntity);

        // when
        TechContentEntity result = techContentEntityMapper.from(command);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProvider()).isEqualTo(providerEntity);
        assertThat(result.getUrl()).isEqualTo("url");
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getPublishedDate()).isEqualTo(LocalDate.of(2021, 1, 1));
        assertThat(result.getImage()).isEqualTo(imageEntity);
        assertThat(result.getSummary()).isEqualTo("summary");
        assertThat(result.getCategories()).containsExactly(TechCategory.SERVER, TechCategory.SW_ENGINEERING);
    }
}