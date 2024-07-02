package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.service.ProviderRegisterCommand;
import com.nocommittoday.techswipe.content.service.ProviderRegisterService;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
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

    private final ImageStoreService imageStoreService;

    @PostMapping("/api/content/admin/providers")
    ResponseEntity<ProviderRegisterResponse> register(
            @RequestBody @Valid final ProviderRegisterRequest request
    ) {
        final Image.Id imageId = imageStoreService.store(request.iconUrl(), "provider");
        final TechContentProvider.Id providerId = providerRegisterService.register(
                new ProviderRegisterCommand(
                        request.type(),
                        request.title(),
                        request.url(),
                        imageId
                )
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ProviderRegisterResponse(providerId.value()));
    }
}
