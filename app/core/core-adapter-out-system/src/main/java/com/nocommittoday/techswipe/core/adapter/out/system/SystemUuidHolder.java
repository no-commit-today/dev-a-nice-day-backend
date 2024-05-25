package com.nocommittoday.techswipe.core.adapter.out.system;

import com.nocommittoday.techswipe.core.application.port.out.UuidHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SystemUuidHolder implements UuidHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
