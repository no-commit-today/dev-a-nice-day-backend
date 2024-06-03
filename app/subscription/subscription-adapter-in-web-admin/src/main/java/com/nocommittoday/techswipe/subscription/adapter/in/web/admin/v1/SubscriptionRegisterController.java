package com.nocommittoday.techswipe.subscription.adapter.in.web.admin.v1;

import com.nocommittoday.techswipe.subscription.adapter.in.web.admin.v1.request.SubscriptionRegisterRequest;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscriptionRegisterUseCase;
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

    private final SubscriptionRegisterUseCase subscriptionRegisterUseCase;

    @PostMapping("/api/subscription/admin/subscriptions")
    ResponseEntity<Void> register(@Validated @RequestBody final SubscriptionRegisterRequest request) {
        subscriptionRegisterUseCase.register(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
