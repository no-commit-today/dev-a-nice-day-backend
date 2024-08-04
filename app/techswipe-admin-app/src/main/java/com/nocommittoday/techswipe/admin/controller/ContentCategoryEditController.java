package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.admin.controller.request.ContentCategoryEditRequest;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.ContentCategoryEdit;
import com.nocommittoday.techswipe.domain.collection.ContentCategoryEditService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentCategoryEditController {

    private final ContentCategoryEditService categoryEditService;

    public ContentCategoryEditController(ContentCategoryEditService categoryEditService) {
        this.categoryEditService = categoryEditService;
    }

    @PatchMapping("/admin/api/collections/{collectionId}/categories")
    public void edit(
            @PathVariable Long collectionId,
            @RequestBody @Validated ContentCategoryEditRequest request
    ) {
        categoryEditService.editCategory(
                new CollectedContentId(collectionId),
                new ContentCategoryEdit(request.categories())
        );
    }

    @PostMapping("/admin/api/collections/{collectionId}/apply-categories")
    public void apply(@PathVariable Long collectionId) {
        categoryEditService.applyCategoryEdited(new CollectedContentId(collectionId));
    }
}
