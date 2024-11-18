package com.devniceday.batch.param;

import com.devniceday.batch.job.param.TechContentProviderIdJobParameter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechContentProviderIdJobParameterTest {

    @Test
    void 제공자ID_정상설정() {
        TechContentProviderIdJobParameter param = new TechContentProviderIdJobParameter();
        param.setProviderId(123L);
        assertThat(param.getProviderId()).isEqualTo(123L);
    }

    @Test
    void 제공자ID_설정되지않았을때_null반환() {
        TechContentProviderIdJobParameter param = new TechContentProviderIdJobParameter();
        assertThat(param.getProviderId()).isNull();
    }

    @Test
    void 제공자ID_null값설정() {
        TechContentProviderIdJobParameter param = new TechContentProviderIdJobParameter();
        param.setProviderId(null);
        assertThat(param.getProviderId()).isNull();
    }

}