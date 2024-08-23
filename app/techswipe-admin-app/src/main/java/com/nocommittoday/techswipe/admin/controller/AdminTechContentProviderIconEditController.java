package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.AdminTechContentProviderIconEditRequest;
import com.nocommittoday.techswipe.admin.domain.AdminTechContentProviderIconEditService;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.image.ImageId;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import com.nocommittoday.techswipe.infrastructure.aws.image.ImageStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AdminTechContentProviderIconEditController {

    private final ImageStore imageStore;
    private final AdminTechContentProviderIconEditService techContentProviderIconEditService;

    public AdminTechContentProviderIconEditController(
            ImageStore imageStore,
            AdminTechContentProviderIconEditService techContentProviderIconEditService
    ) {
        this.imageStore = imageStore;
        this.techContentProviderIconEditService = techContentProviderIconEditService;
    }

    @PatchMapping("/admin/api/providers/{providerId}/icon")
    void edit(
            AdminApiUser adminApiUser,
            @PathVariable Long providerId,
            @RequestBody @Validated AdminTechContentProviderIconEditRequest request
    ) {
        ImageId iconId = Optional.ofNullable(request.iconUrl())
                .map(url -> imageStore.store(url, "provider").get())
                .orElse(null);
        techContentProviderIconEditService.edit(new TechContentProviderId(providerId), iconId);
    }
}
