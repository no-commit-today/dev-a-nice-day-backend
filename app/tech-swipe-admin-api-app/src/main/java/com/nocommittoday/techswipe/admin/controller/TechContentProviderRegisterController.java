package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.service.TechContentProviderRegisterCommand;
import com.nocommittoday.techswipe.content.service.TechContentProviderRegisterService;
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
public class TechContentProviderRegisterController {

    private final TechContentProviderRegisterService techContentProviderRegisterService;

    private final ImageStoreService imageStoreService;

    @PostMapping("/api/content/admin/providers")
    ResponseEntity<TechProviderRegisterResponse> register(
            @RequestBody @Valid final TechContentProviderRegisterRequest request
    ) {
        final Image.Id imageId = imageStoreService.store(request.iconUrl(), "provider");
        final TechContentProvider.Id providerId = techContentProviderRegisterService.register(
                new TechContentProviderRegisterCommand(
                        request.type(),
                        request.title(),
                        request.url(),
                        imageId
                )
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new TechProviderRegisterResponse(providerId.value()));
    }
}