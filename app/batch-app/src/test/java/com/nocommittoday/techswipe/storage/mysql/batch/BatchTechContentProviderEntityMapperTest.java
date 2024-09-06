package com.nocommittoday.techswipe.storage.mysql.batch;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BatchTechContentProviderEntityMapperTest {

    @InjectMocks
    private BatchTechContentProviderEntityMapper techContentProviderEntityMapper;

    @Mock
    private TechContentProviderJpaRepository techContentProviderJpaRepository;

    @Mock
    private BatchImageEntityMapper imageEntityMapper;

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        TechContentProviderEntity entity = mock(TechContentProviderEntity.class);
        given(techContentProviderJpaRepository.getReferenceById(1L)).willReturn(entity);

        // when
        TechContentProviderEntity result = techContentProviderEntityMapper.from(new TechContentProviderId(1));

        // then
        assertThat(result).isEqualTo(entity);
    }

}