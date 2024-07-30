package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.subscription.service.SubscriptionRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionRegisterController {

    private final SubscriptionRegisterService subscriptionRegisterService;

    @PutMapping("/api/subscription/admin/providers/{providerId}/subscription")
    public ResponseEntity<Void> register(
            @PathVariable Long providerId,
            @Validated @RequestBody SubscriptionRegisterRequest request
    ) {
        subscriptionRegisterService.register(request.toDomain(
                new TechContentProviderId(providerId)));
        return ResponseEntity.ok().build();
    }
}
