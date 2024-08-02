package com.nocommittoday.techswipe.storage.mysql.content;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderEntityTest {

    @Test
    void 도메인_엔티티_ID로부터_생성할_수_있다() {
        // given
        TechContentProviderId id = new TechContentProviderId(1);

        // when
        TechContentProviderEntity result = TechContentProviderEntity.from(id);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

}