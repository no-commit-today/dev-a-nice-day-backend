package com.nocommittoday.techswipe.domain.content.provider;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.DomainValidationException;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderJpaRepository;
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
    private TechContentProviderJpaRepository techContentProviderJpaRepository;

    @Test
    void 컨텐츠_제공자가_존재하지_않을_경우_예외를_발생시킨다() {
        // given
        TechContentProviderId id = new TechContentProviderId(1L);
        given(techContentProviderJpaRepository.existsByIdAndDeletedIsFalse(id.value()))
                .willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> techContentProviderIdValidator.validate(id))
                .isInstanceOf(DomainValidationException.class);
    }
}