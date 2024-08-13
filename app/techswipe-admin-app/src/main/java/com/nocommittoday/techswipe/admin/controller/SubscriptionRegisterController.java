package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.SubscriptionRegisterRequest;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionRegisterService;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionRegisterController {

    private final SubscriptionRegisterService subscriptionRegisterService;

    public SubscriptionRegisterController(
            SubscriptionRegisterService subscriptionRegisterService
    ) {
        this.subscriptionRegisterService = subscriptionRegisterService;
    }

    @PutMapping("/admin/api/providers/{providerId}/subscription")
    public ResponseEntity<Void> register(
            AdminApiUser adminApiUser,
            @PathVariable Long providerId,
            @Validated @RequestBody SubscriptionRegisterRequest request
    ) {
        subscriptionRegisterService.register(request.toDomain(
                new TechContentProviderId(providerId)));
        return ResponseEntity.ok().build();
    }
}
