package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.service.ProviderRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProviderRegisterController {

    private final ProviderRegisterService providerRegisterService;

    @PostMapping("/api/content/admin/contents")
    ResponseEntity<ProviderRegisterResponse> register(@RequestBody @Valid final ProviderRegisterRequest request) {
        final TechContentProvider.TechContentProviderId providerId = providerRegisterService.register(
                request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProviderRegisterResponse(providerId.value()));
    }
}
