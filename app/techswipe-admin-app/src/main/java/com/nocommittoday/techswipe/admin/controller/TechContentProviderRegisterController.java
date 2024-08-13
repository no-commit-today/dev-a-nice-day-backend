package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.TechContentProviderRegisterRequest;
import com.nocommittoday.techswipe.admin.controller.response.TechContentProviderRegisterResponse;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.content.provider.TechContentProviderRegisterCommand;
import com.nocommittoday.techswipe.domain.content.provider.TechContentProviderRegisterService;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import com.nocommittoday.techswipe.infrastructure.image.ImageStore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TechContentProviderRegisterController {

    private final TechContentProviderRegisterService techContentProviderRegisterService;
    private final ImageStore imageStore;

    public TechContentProviderRegisterController(
            TechContentProviderRegisterService techContentProviderRegisterService,
            ImageStore imageStore
    ) {
        this.techContentProviderRegisterService = techContentProviderRegisterService;
        this.imageStore = imageStore;
    }

    @PostMapping("/admin/api/providers")
    ResponseEntity<TechContentProviderRegisterResponse> register(
            AdminApiUser adminApiUser,
            @RequestBody @Valid TechContentProviderRegisterRequest request
    ) {
        ImageId iconId = Optional.ofNullable(request.iconUrl())
                .map(url -> imageStore.store(url, "provider").get())
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
