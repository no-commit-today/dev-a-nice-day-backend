package com.nocommittoday.techswipe.domain.content;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderTypeTest {

    @Test
    void value() {
        assertThat(TechContentProviderType.DOMESTIC_COMPANY_BLOG).isNotNull();
    }
}