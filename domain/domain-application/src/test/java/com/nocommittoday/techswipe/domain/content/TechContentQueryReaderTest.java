package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.content.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.test.TechContentEntityBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentQueryReaderTest {

    @InjectMocks
    private TechContentQueryReader techContentQueryReader;

    @Mock
    private TechContentJpaRepository techContentJpaRepository;

    @Test
    void 쿼리_객체를_반환한다() {
        // given
        TechContentEntity entity = TechContentEntityBuilder.create();
        given(techContentJpaRepository.findById(entity.getId()))
                .willReturn(Optional.of(entity));

        // when
        TechContentQuery techContentQuery = techContentQueryReader.get(new TechContentId(entity.getId()));

        // then
        Assertions.assertThat(techContentQuery.getId()).isNotNull();
    }

    @Test
    void 존재하지_않는_컨텐츠_조회시_예외를_발생시킨다() {
        // given
        given(techContentJpaRepository.findById(1L))
                .willReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> techContentQueryReader.get(new TechContentId(1L)))
                .isInstanceOf(TechContentNotFoundException.class);
    }

    @Test
    void 삭제된_컨텐츠_조회시_예외를_발생시킨다() {
        // given
        TechContentEntity entity = TechContentEntityBuilder.create();
        entity.delete();
        given(techContentJpaRepository.findById(entity.getId()))
                .willReturn(Optional.of(entity));

        // when
        // then
        Assertions.assertThatThrownBy(() -> techContentQueryReader.get(new TechContentId(entity.getId())))
                .isInstanceOf(TechContentNotFoundException.class);
    }
}