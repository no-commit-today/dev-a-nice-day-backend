package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.user.ApiUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    private final UserCategorySaver userCategorySaver;

    public UserCategoryService(UserCategorySaver userCategorySaver) {
        this.userCategorySaver = userCategorySaver;
    }

    public void save(ApiUser apiUser, List<TechCategory> categories) {
        userCategorySaver.save(
                apiUser.getUserId(),
                categories.stream().distinct().sorted().toList()
        );
    }
}
