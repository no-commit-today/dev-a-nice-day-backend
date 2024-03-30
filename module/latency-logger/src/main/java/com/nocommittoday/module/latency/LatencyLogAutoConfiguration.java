package com.nocommittoday.module.latency;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class LatencyLogAutoConfiguration {

    @Bean
    public LatencyLogAspect latencyLogAspect() {
        return new LatencyLogAspect();
    }
}
