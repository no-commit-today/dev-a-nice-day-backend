package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.service.TechContentProviderRegisterCommand;
import com.nocommittoday.techswipe.content.service.TechContentProviderRegisterService;
import com.nocommittoday.techswipe.image.domain.ImageId;
import com.nocommittoday.techswipe.image.service.ImageStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TechContentProviderRegisterController {

    private final TechContentProviderRegisterService techContentProviderRegisterService;

    private final ImageStoreService imageStoreService;

    @PostMapping("/api/content/admin/providers")
    ResponseEntity<TechContentProviderRegisterResponse> register(
            @RequestBody @Valid TechContentProviderRegisterRequest request
    ) {
        ImageId iconId = Optional.ofNullable(request.iconUrl())
                .map(url -> imageStoreService.store(url, "provider").get())
                .orElse(null);
        TechContentProviderId providerId = techContentProviderRegisterService.register(
                new TechContentProviderRegisterCommand(
                        request.type(),
                        request.title(),
                        request.url(),
                        iconId
                )
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TechContentProviderRegisterResponse.from(providerId));
    }
}
