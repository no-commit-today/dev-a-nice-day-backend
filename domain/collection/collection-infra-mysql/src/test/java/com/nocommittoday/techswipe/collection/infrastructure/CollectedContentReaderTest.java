package com.nocommittoday.techswipe.collection.infrastructure;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionNotFoundException;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CollectedContentReaderTest {

    @InjectMocks
    private CollectedContentReader reader;

    @Mock
    private CollectedContentJpaRepository collectedContentJpaRepository;

    @Test
    void 수집된_컨텐츠를_조회할_수_있다() {
        // given
        final CollectedContentEntity entity = mock(CollectedContentEntity.class);
        given(entity.isUsed()).willReturn(true);
        given(entity.toDomain()).willReturn(mock(CollectedContent.class));
        given(collectedContentJpaRepository.findById(1L)).willReturn(Optional.of(entity));

        // when
        final CollectedContent result = reader.get(new CollectedContent.CollectedContentId(1L));

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 삭제된_엔티티는_조회할_수_없다() {
        // given
        final CollectedContentEntity entity = mock(CollectedContentEntity.class);
        given(entity.isUsed()).willReturn(false);
        given(collectedContentJpaRepository.findById(1L)).willReturn(Optional.of(entity));

        // when, then
        assertThatThrownBy(() -> reader.get(new CollectedContent.CollectedContentId(1L)))
                .isInstanceOf(CollectionNotFoundException.class);
    }

}