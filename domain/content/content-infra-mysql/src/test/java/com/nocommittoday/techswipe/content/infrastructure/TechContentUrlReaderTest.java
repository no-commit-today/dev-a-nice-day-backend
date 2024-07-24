package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentId;
import com.nocommittoday.techswipe.content.domain.exception.TechContentNotFoundException;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentUrlReaderTest {

    @InjectMocks
    private TechContentUrlReader techContentUrlReader;

    @Mock
    private TechContentJpaRepository techContentJpaRepository;

    @Test
    void 컨텐츠가_존재하지_않을경우_예외를_발생시킨다() {
        // given
        given(techContentJpaRepository.findUrlByIdAndDeletedIsFalse(1L))
                .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> techContentUrlReader.get(new TechContentId(1L)))
                .isInstanceOf(TechContentNotFoundException.class);
    }

}