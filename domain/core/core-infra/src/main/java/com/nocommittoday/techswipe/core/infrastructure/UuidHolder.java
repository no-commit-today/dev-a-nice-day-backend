package com.nocommittoday.techswipe.core.infrastructure;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidHolder {

    public String random() {
        return UUID.randomUUID().toString();
    }
}
