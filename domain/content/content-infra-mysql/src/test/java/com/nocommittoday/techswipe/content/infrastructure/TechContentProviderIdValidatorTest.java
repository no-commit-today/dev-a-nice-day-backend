package com.nocommittoday.techswipe.content.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechContentProviderIdValidatorTest {

    @InjectMocks
    private TechContentProviderIdValidator techContentProviderIdValidator;

    @Mock
    private TechContentProviderExistsReader techContentProviderExistsReader;

    @Test
    void 컨텐츠_제공자가_존재하지_않을_경우_예외를_발생시킨다() {
        // given
        final TechContentProvider.Id id = new TechContentProvider.Id(1L);
        given(techContentProviderExistsReader.exists(id)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> techContentProviderIdValidator.validate(id))
                .isInstanceOf(TechContentProviderNotFoundException.class);
    }
}