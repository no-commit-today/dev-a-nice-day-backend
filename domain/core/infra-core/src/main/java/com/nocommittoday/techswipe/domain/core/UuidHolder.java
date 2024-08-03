package com.nocommittoday.techswipe.domain.core;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidHolder {

    public UUID random() {
        return UUID.randomUUID();
    }
}
