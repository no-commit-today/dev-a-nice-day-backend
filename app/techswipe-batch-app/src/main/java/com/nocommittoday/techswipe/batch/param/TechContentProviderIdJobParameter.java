package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Nullable;

public class TechContentProviderIdJobParameter {

    public static final String NAME = "TechContentProviderIdJobParameter";

    @Nullable
    private TechContentProviderId providerId;

    @Value("#{jobParameters['provider.id']}")
    public void setProviderId(@Nullable Long providerId) {
        this.providerId = providerId != null ? new TechContentProviderId(providerId) : null;
    }

    @Nullable
    public TechContentProviderId getProviderId() {
        return providerId;
    }
}
