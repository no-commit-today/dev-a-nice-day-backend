package com.nocommittoday.techswipe.controller.content.v1;

import com.nocommittoday.techswipe.controller.content.v1.request.UserCategorySaveRequest;
import com.nocommittoday.techswipe.controller.content.v1.response.UserCategoryResponse;
import com.nocommittoday.techswipe.domain.content.UserCategoryService;
import com.nocommittoday.techswipe.domain.user.ApiUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCategoryController {

    private final UserCategoryService userCategoryService;

    public UserCategoryController(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    @GetMapping("/api/content/v1/user/categories")
    public UserCategoryResponse get(ApiUser apiUser) {
        return UserCategoryResponse.from(userCategoryService.get(apiUser));
    }

    @PutMapping("/api/content/v1/user/categories")
    public void save(ApiUser apiUser, @RequestBody @Valid UserCategorySaveRequest request) {
        userCategoryService.save(apiUser, request.categories());
    }
}
