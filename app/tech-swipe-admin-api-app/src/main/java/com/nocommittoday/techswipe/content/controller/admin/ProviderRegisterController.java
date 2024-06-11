package com.nocommittoday.techswipe.content.controller.admin;

import com.nocommittoday.techswipe.content.controller.admin.request.ProviderRegisterRequest;
import com.nocommittoday.techswipe.content.controller.admin.response.ProviderRegisterResponse;
import com.nocommittoday.techswipe.content.application.port.in.ProviderRegisterUseCase;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
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

    private final ProviderRegisterUseCase providerRegisterUseCase;

    @PostMapping("/api/content/admin/contents")
    ResponseEntity<ProviderRegisterResponse> register(@RequestBody @Valid final ProviderRegisterRequest request) {
        final TechContentProvider.TechContentProviderId providerId = providerRegisterUseCase.register(
                request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProviderRegisterResponse(providerId.value()));
    }
}
