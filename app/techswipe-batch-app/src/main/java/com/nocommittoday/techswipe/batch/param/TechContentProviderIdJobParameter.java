package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class TechContentProviderIdJobParameter {

    public static final String NAME = "TechContentProviderIdJobParameter";

    private TechContentProviderId providerId;

    @Value("#{jobParameters['provider.id']}")
    public void setProviderId(Long providerId) {
        this.providerId = new TechContentProviderId(providerId);
    }
}
