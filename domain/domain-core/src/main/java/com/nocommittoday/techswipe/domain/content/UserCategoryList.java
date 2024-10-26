package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.UserId;

import java.util.List;

public class UserCategoryList {

    private final UserId userId;

    private final List<TechCategory> categories;

    public UserCategoryList(UserId userId, List<TechCategory> categories) {
        this.userId = userId;
        this.categories = categories;
    }

    public UserId getUserId() {
        return userId;
    }

    public List<TechCategory> getCategories() {
        return categories;
    }
}
