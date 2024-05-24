package com.nocommittoday.techswipe.core.adapter.out.system;

import com.nocommittoday.techswipe.core.application.port.out.UuidHolder;

import java.util.UUID;

public class SystemUuidHolder implements UuidHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
