package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TechContentProviderIdListJobParameterTest {

    @Test
    void 문자열로_받은_provider_ids_파라미터를_파싱한다() {
        // given
        final String parameter = "[1;2;3]";
        final TechContentProviderIdListJobParameter techContentProviderIdListJobParameter = new TechContentProviderIdListJobParameter();

        // when
        techContentProviderIdListJobParameter.setProviderId(parameter);

        // then
        assertThat(techContentProviderIdListJobParameter.getProviderIdList()).containsExactly(
                new TechContentProviderId(1L),
                new TechContentProviderId(2L),
                new TechContentProviderId(3L)
        );
    }

    @Test
    void provider_ids_파라미터가_숫자가_아닌_문자열이면_예외를_던진다() {
        // given
        final String parameter = "[1;2;3a]";
        final TechContentProviderIdListJobParameter techContentProviderIdListJobParameter = new TechContentProviderIdListJobParameter();

        // when & then
        assertThatThrownBy(() -> techContentProviderIdListJobParameter.setProviderId(parameter))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void provider_ids_파라미터가_중복이_있으면_중복을_제거한다() {
        // given
        final String parameter = "[1;2;2;3]";
        final TechContentProviderIdListJobParameter techContentProviderIdListJobParameter = new TechContentProviderIdListJobParameter();

        // when
        techContentProviderIdListJobParameter.setProviderId(parameter);

        // then
        assertThat(techContentProviderIdListJobParameter.getProviderIdList()).containsExactly(
                new TechContentProviderId(1L),
                new TechContentProviderId(2L),
                new TechContentProviderId(3L)
        );
    }

    @Test
    void provider_ids_파라미터가_와일드카드이면_null을_반환한다() {
        // given
        final TechContentProviderIdListJobParameter techContentProviderIdListJobParameter = new TechContentProviderIdListJobParameter();

        // when
        techContentProviderIdListJobParameter.setProviderId("[*]");

        // then
        assertThat(techContentProviderIdListJobParameter.getProviderIdList()).isNull();
    }
}