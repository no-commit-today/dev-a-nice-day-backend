package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.AdminContentCategoryEditRequest;
import com.nocommittoday.techswipe.admin.domain.AdminContentCategoryEditService;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminContentCategoryEditController {

    private final AdminContentCategoryEditService categoryEditService;

    public AdminContentCategoryEditController(AdminContentCategoryEditService categoryEditService) {
        this.categoryEditService = categoryEditService;
    }

    @PatchMapping("/admin/api/collections/{collectionId}/categories")
    public void edit(
            AdminApiUser adminApiUser,
            @PathVariable Long collectionId,
            @RequestBody @Validated AdminContentCategoryEditRequest request
    ) {
        categoryEditService.edit(
                new CollectedContentId(collectionId),
                new CollectionCategoryList(request.categories())
        );
    }
}
