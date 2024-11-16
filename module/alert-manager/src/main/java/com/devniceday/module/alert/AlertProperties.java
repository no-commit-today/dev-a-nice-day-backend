package com.devniceday.module.alert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.alert")
public record AlertProperties(
        AlertType type
) {
}
