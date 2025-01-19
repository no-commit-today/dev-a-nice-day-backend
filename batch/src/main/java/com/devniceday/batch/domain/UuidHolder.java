package com.devniceday.batch.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidHolder {

    public UUID random() {
        return UUID.randomUUID();
    }
}
