package com.nocommittoday.techswipe.controller.content.v1.response;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.content.UserCategoryList;

import java.util.List;

public record UserCategoryResponse(
        List<TechCategory> categories
) {

    public static UserCategoryResponse from(UserCategoryList userCategoryList) {
        return new UserCategoryResponse(
                userCategoryList.getCategories()
        );
    }
}
