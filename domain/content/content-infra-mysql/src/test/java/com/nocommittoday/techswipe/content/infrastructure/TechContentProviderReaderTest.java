package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.exception.TechContentProviderNotFoundException;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentProviderReaderTest {

    @InjectMocks
    private TechContentProviderReader techContentProviderReader;

    @Mock
    private TechContentProviderJpaRepository providerJpaRepository;

    @Test
    void 컨텐츠_제공자를_조회할_수_있다() {
        // given
        final TechContentProviderEntity providerEntity = new TechContentProviderEntity(
                1L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                null
        );
        given(providerJpaRepository.findById(1L))
                .willReturn(Optional.of(providerEntity));

        // when
        final TechContentProvider provider = techContentProviderReader.get(new TechContentProvider.Id(1L));

        // then
        assertThat(provider).isNotNull();
    }

    @Test
    void 삭제된_컨텐츠_제공자는_조회할_수_없다() {
        // given
        final TechContentProviderEntity providerEntity = new TechContentProviderEntity(
                1L,
                TechContentProviderType.DOMESTIC_COMPANY_BLOG,
                "title",
                "url",
                null
        );
        providerEntity.delete();

        given(providerJpaRepository.findById(1L))
                .willReturn(Optional.of(providerEntity));

        // when
        // then
        assertThatThrownBy(() -> techContentProviderReader.get(new TechContentProvider.Id(1L)))
                .isInstanceOf(TechContentProviderNotFoundException.class);
    }


}