package com.devniceday.batch.param;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Nullable;

public class TechContentProviderIdJobParameter {

    @Nullable
    private Long providerId;

    @Value("#{jobParameters['provider.id']}")
    public void setProviderId(@Nullable Long providerId) {
        this.providerId = providerId;
    }

    @Nullable
    public Long getProviderId() {
        return providerId;
    }
}
