package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.subscription.service.SubscriptionRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class SubscriptionRegisterController {

    private final SubscriptionRegisterService subscriptionRegisterService;

    @PostMapping("/api/subscription/admin/subscriptions")
    ResponseEntity<Void> register(@Validated @RequestBody final SubscriptionRegisterRequest request) {
        subscriptionRegisterService.register(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
