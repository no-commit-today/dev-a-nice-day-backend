package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.AdminTechContentProviderRegisterRequest;
import com.nocommittoday.techswipe.admin.controller.response.AdminTechContentProviderRegisterResponse;
import com.nocommittoday.techswipe.admin.domain.AdminTechContentProviderRegisterCommand;
import com.nocommittoday.techswipe.admin.domain.AdminTechContentProviderRegisterService;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
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
public class AdminTechContentProviderRegisterController {

    private final AdminTechContentProviderRegisterService techContentProviderRegisterService;
    private final ImageStore imageStore;

    public AdminTechContentProviderRegisterController(
            AdminTechContentProviderRegisterService techContentProviderRegisterService,
            ImageStore imageStore
    ) {
        this.techContentProviderRegisterService = techContentProviderRegisterService;
        this.imageStore = imageStore;
    }

    @PostMapping("/admin/api/providers")
    ResponseEntity<AdminTechContentProviderRegisterResponse> register(
            AdminApiUser adminApiUser,
            @RequestBody @Valid AdminTechContentProviderRegisterRequest request
    ) {
        ImageId iconId = Optional.ofNullable(request.iconUrl())
                .map(url -> imageStore.store(url, "provider").get())
                .orElse(null);
        TechContentProviderId providerId = techContentProviderRegisterService.register(
                new AdminTechContentProviderRegisterCommand(
                        request.type(),
                        request.title(),
                        request.url(),
                        iconId
                )
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AdminTechContentProviderRegisterResponse.from(providerId));
    }
}
