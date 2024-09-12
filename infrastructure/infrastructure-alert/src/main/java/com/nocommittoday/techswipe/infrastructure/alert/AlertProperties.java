package com.nocommittoday.techswipe.infrastructure.alert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.alert")
public record AlertProperties(
        AlertType type
) {
}
