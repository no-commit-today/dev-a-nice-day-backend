package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class ProviderIdJobParameters {

    public static final String NAME = "ProviderIdJobParameters";

    private TechContentProvider.TechContentProviderId providerId;

    @Value("#{jobParameters['provider.id']}")
    public void setProviderId(final Long providerId) {
        this.providerId = new TechContentProvider.TechContentProviderId(providerId);
    }
}
