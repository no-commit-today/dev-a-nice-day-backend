package com.nocommittoday.techswipe.core.adapter.in.web.servlet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HealthController {

    @GetMapping("/health")
    ResponseEntity<Void> health() {
        return ResponseEntity.ok().build();
    }
}
