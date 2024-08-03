package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import org.springframework.beans.factory.annotation.Value;

public class TechContentProviderIdJobParameter {

    public static final String NAME = "TechContentProviderIdJobParameter";

    private TechContentProviderId providerId;

    @Value("#{jobParameters['provider.id']}")
    public void setProviderId(Long providerId) {
        this.providerId = new TechContentProviderId(providerId);
    }

    public TechContentProviderId getProviderId() {
        return providerId;
    }
}
